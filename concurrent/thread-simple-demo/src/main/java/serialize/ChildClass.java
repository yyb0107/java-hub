package serialize;

import java.io.Serializable;

/**
 * @author Bingo
 * @title: ParentClass
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/26  22:01
 */
public class ChildClass extends ParentClass implements Serializable{


    private String cname;
    private int cid;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return super.toString()+" ChildClass{" +
                "cname='" + cname + '\'' +
                ", cid=" + cid +
                '}';
    }
}
