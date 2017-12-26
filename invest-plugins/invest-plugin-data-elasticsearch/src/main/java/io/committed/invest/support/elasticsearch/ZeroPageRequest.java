package io.committed.invest.support.elasticsearch;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ZeroPageRequest implements Pageable {

  public static ZeroPageRequest AGGREGATION_ONLY = new ZeroPageRequest();

  @Override
  public Pageable first() {
    return this;
  }

  @Override
  public long getOffset() {
    return 0;
  }

  @Override
  public int getPageNumber() {
    return 0;
  }

  @Override
  public int getPageSize() {
    return 0;
  }

  @Override
  public Sort getSort() {
    // nullable
    return null;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  public Pageable next() {
    return this;
  }

  @Override
  public Pageable previousOrFirst() {
    return this;
  }

}
