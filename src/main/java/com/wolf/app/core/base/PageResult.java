package com.wolf.app.core.base;

import java.util.List;

public interface PageResult<T> extends Result<T> {

	List<T> getRows();

	long getTotalCount();

	int getTotalPage();
}
