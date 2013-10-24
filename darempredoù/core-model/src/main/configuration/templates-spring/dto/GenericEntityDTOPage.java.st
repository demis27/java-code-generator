package $defaultPackageName$.dto;

import java.util.ArrayList;
import java.util.List;

public class GenericEntityDTOPage<T> {

    private List<T> elements = new ArrayList<T>();

    private int pageSize;

    private int pageNumber;

    private long totalElements;

    private int totalPage;

    private boolean hasNext;

    private boolean hasPrevious;

    private String nextURI;

    private String previousURI;

    public GenericEntityDTOPage() {
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void hasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }

    public void hasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public String getNextUri() {
        return nextURI;
    }

    public void setNextUri(String nextURI) {
        this.nextURI = nextURI;
    }

    public String getPreviousUri() {
        return previousURI;
    }

    public void setPreviousUri(String previousURI) {
        this.previousURI = previousURI;
    }

}
