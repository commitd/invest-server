package io.committed.invest.support.data.utils;

import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/** Spring Pageable based on Offset-limit rather than Pages.. */
public class OffsetLimitPagable implements Pageable {

  private final long offset;
  private final int limit;

  public OffsetLimitPagable(final long offset, final int limit) {
    this.offset = offset;
    this.limit = limit;
  }

  @Override
  public int getPageNumber() {
    return (int) (offset / limit);
  }

  @Override
  public int getPageSize() {
    return limit;
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Sort getSort() {
    return Sort.unsorted();
  }

  @Override
  public Pageable next() {
    return new OffsetLimitPagable(offset + limit, limit);
  }

  @Override
  public Pageable previousOrFirst() {
    return new OffsetLimitPagable(Math.max(offset - limit, 0), limit);
  }

  @Override
  public Pageable first() {
    return new OffsetLimitPagable(0, limit);
  }

  @Override
  public boolean hasPrevious() {
    return offset > 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, limit);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final OffsetLimitPagable other = (OffsetLimitPagable) obj;
    return limit == other.limit && offset == other.offset;
  }
}
