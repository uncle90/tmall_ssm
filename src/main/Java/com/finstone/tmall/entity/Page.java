package com.finstone.tmall.entity;

public class Page {
    int start; //记录开始编号

    int count; //每页显示数量

    int total; //记录总条数

    //href="?start=${page.start-page.count}${page.param}"
    //分页时，在url中存放除分页信息外的其他数据，如admin_property_list?start=5【&cid=83】
    String param;

    public static final int defaultCount = 5; //默认每页显示5条

    //初始化分页大小
    public Page() {
        count = defaultCount;
    }

    //配合MySQL分页
    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 根据count和total计算分页总数
     * @return
     */
    public int getTotalPage(){ ;
        if (0 == total%count) {
            return total/count;
        } else {
            return total/count + 1;
        }
    }

    /**
     * 计算最后一页第一条记录的编号(0,1,2,..,count-1)
     * @return
     */
    public int getLast(){
        int last;
        if (0 == total%count) {
            last = total - count;
        } else {
            last = total - total%count;
        }
        last = last<0?0:last;
        return last;
    }

    /**
     * 判断是否有前一页
     * @return
     */
    public boolean isHasPrevious(){
        if (start == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否有后一页
     * @return
     */
    public boolean isHasNext(){
        if (start == getLast()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        /**
         * 根据JavaBean规范，如果字段property是boolean类型的，则其getter方法名为isProperty()。
         * 所以isProperty()对应的字段名称就是property，而不是isProperty。
         * hasPrevious
         * hasNext
         * totalPage
         */
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                ", hasPrevious=" + isHasPrevious() +
                ", hasNext=" + isHasNext() +
                ", totalPage=" + getTotalPage() +
                ", total=" + total +
                '}';
    }

}
