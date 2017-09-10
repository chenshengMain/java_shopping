package domain;

import java.util.List;

public class PageBean<T> {
	@Override
	public String toString() {
		return "PageBean [pageIndex=" + pageIndex + ", totalPage=" + totalPage + ", count=" + count + ", pageList="
				+ pageList + "]";
	}

	private int pageIndex;
	private int totalPage;
	private int count;
	private List<T> pageList;


	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
}
