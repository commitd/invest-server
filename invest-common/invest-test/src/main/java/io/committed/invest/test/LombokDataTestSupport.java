package io.committed.invest.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Test;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ClassInfo;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

// Based on
@Slf4j
public class LombokDataTestSupport {

  public void testPackage(final Class<?> clazz) {

    final String packageName = clazz.getPackage().getName();
    final ScanResult scanResult = new FastClasspathScanner(packageName)
        .ignoreParentClassLoaders()
        .scan();

    scanResult.getClassNameToClassInfo().values().stream()
        // Not sure why this is requied, but sometimes come backs with java.lang.Object otherwise
        .filter(i -> i.getClassName().startsWith(packageName))
        .forEach(this::testClassInfo);
  }

  public void testClassInfo(final ClassInfo info) {
    try {
      final Class<?> loadedClass = getClass().getClassLoader().loadClass(info.getClassName());
      testClass(loadedClass);
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void testClass(final Class<?> clazz) {
    if (isTestClass(clazz)) {
      return;
    }

    final int modifiers = clazz.getModifiers();
    if (Modifier.isInterface(modifiers)) {
      return;
    }

    try {
      if (Modifier.isAbstract(modifiers)) {
        testAbstractClass(clazz);
      } else {
        testConcreteClass(clazz);
      }
    } catch (final AssertionError | Exception e) {
      rethrowWithClass(clazz, e);
    }
  }


  private boolean isTestClass(final Class<?> clazz) {
    for (final Method m : clazz.getMethods()) {
      if (m.isAnnotationPresent(Test.class)) {
        return true;
      }
    }
    return false;
  }

  protected void testAbstractClass(final Class<?> clazz) {
    // Test #equals and #hashCode
    EqualsVerifier.forClass(clazz).suppress(Warning.STRICT_INHERITANCE,
        Warning.NONFINAL_FIELDS).verify();
  }

  protected void testConcreteClass(final Class<?> clazz)
      throws InstantiationException, IllegalAccessException, NotFoundException, CannotCompileException {

    // Skip abstract classes, interfaces and this class.
    final int modifiers = clazz.getModifiers();
    if (Modifier.isAbstract(modifiers) ||
        clazz.equals(this.getClass())) {
      return;
    }

    try {
      final Constructor<?> constructor = clazz.getConstructor();
    } catch (final NoSuchMethodException e) {
      log.warn("Unable to test {} as no default constructor", clazz.getSimpleName());
      return;
    }
    // Test getters, setters and #toString
    BeanTestSupport.test(clazz);

    // Test #equals and #hashCode

    EqualsVerifier.forClass(clazz).withRedefinedSuperclass()
        .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();

    // Verify not equals with subclass (for code coverage with Lombok)
    if (!Modifier.isFinal(modifiers)) {
      Assert.assertFalse(clazz.newInstance().equals(createSubClassInstance(clazz.getName())));
    }
  }

  private void rethrowWithClass(final Class<?> clazz, final Throwable e) throws AssertionError {
    throw new AssertionError(String.format("%s: %s", clazz.getSimpleName(), e.getMessage()), e);
  }

  // Adapted from http://stackoverflow.com/questions/17259421/java-creating-a-subclass-dynamically
  static Object createSubClassInstance(final String superClassName)
      throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {

    final ClassPool pool = ClassPool.getDefault();

    // Create the class.
    final CtClass subClass = pool.makeClass(superClassName + "Extended");
    final CtClass superClass = pool.get(superClassName);
    subClass.setSuperclass(superClass);
    subClass.setModifiers(Modifier.PUBLIC);

    // Add a constructor which will call super( ... );
    final CtClass[] params = new CtClass[] {};
    final CtConstructor ctor = CtNewConstructor.make(params, null, CtNewConstructor.PASS_PARAMS,
        null, null, subClass);
    subClass.addConstructor(ctor);

    // Add a canEquals method
    final CtMethod ctmethod = CtNewMethod
        .make("public boolean canEqual(Object o) { return o instanceof " + superClassName + "Extended; }", subClass);
    subClass.addMethod(ctmethod);

    return subClass.toClass().newInstance();
  }

}
