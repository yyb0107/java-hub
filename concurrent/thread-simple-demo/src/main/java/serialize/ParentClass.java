package serialize;

import java.io.Serializable;

/**
 * @author Bingo
 * @title: ParentClass
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/26  22:01
 */
public class ParentClass {
    private String pname;
    private int pid;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "ParentClass{" +
                "pname='" + pname + '\'' +
                ", pid=" + pid +
                '}';
    }
}
