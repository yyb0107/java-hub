package generate.model;

public class Subject {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_subject.id
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_subject.userid
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    private Integer userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_subject.title
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_subject.link
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    private String link;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_subject.description
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_subject.id
     *
     * @return the value of t_subject.id
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_subject.id
     *
     * @param id the value for t_subject.id
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_subject.userid
     *
     * @return the value of t_subject.userid
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_subject.userid
     *
     * @param userid the value for t_subject.userid
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_subject.title
     *
     * @return the value of t_subject.title
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_subject.title
     *
     * @param title the value for t_subject.title
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_subject.link
     *
     * @return the value of t_subject.link
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_subject.link
     *
     * @param link the value for t_subject.link
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_subject.description
     *
     * @return the value of t_subject.description
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_subject.description
     *
     * @param description the value for t_subject.description
     *
     * @mbg.generated Sun Jun 02 22:59:38 CST 2019
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}