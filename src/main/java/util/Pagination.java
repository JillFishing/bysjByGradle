package util;

public class Pagination {
    public String toLimitClause(int totalNum){
        this.totalNum = totalNum;
        int offset = (this.pageNo - 1) * this.pageSize;
        return " Limit " + offset + "," +this.totalNum;
    }

    public Pagination(int pageSize,int pageNo){
        if (pageSize>0){
            this.pageSize = pageSize;
        }else {
            this.pageSize = 5;
        }
        if (pageNo>0){
            this.pageNo = pageNo;
        }else {
            this.pageNo = 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalNum() {
        return totalNum;
    }

    private int pageSize = 5;
    private int pageNo = 1;
    private int totalNum = 1;
}
