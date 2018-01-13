package graduating.project.com.apm.object;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tuan on 27/11/2017.
 */

public class Task implements Serializable {
    private int id;
    private String time_require;
    private int status;
    private String time_created;
    private String deadline;
    private String name;
    private int count;
    private String cover_color;
    private String cover_paper;
    private String bookbinding_type;
    private String paper_info;
    private String other_require;
    private String file;
    private int type;
    private List<Issue> issues;
    private List<Assign> assign;

    public Task() {
    }

    public Task(int id,
                String time_require,
                int status,
                String time_created,
                String deadline,
                String name,
                int count,
                String cover_color,
                String cover_paper,
                String bookbinding_type,
                String paper_info,
                String other_require,
                String file,
                int type,
                List<Issue> issues,
                List<Assign> assign) {
        this.id = id;
        this.time_require = time_require;
        this.status = status;
        this.time_created = time_created;
        this.deadline = deadline;
        this.name = name;
        this.count = count;
        this.cover_color = cover_color;
        this.cover_paper = cover_paper;
        this.bookbinding_type = bookbinding_type;
        this.paper_info = paper_info;
        this.other_require = other_require;
        this.file = file;
        this.type = type;
        this.issues = issues;
        this.assign = assign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_require() {
        return time_require;
    }

    public void setTime_require(String time_require) {
        this.time_require = time_require;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCover_color() {
        return cover_color;
    }

    public void setCover_color(String cover_color) {
        this.cover_color = cover_color;
    }

    public String getCover_paper() {
        return cover_paper;
    }

    public void setCover_paper(String cover_paper) {
        this.cover_paper = cover_paper;
    }

    public String getBookbinding_type() {
        return bookbinding_type;
    }

    public void setBookbinding_type(String bookbinding_type) {
        this.bookbinding_type = bookbinding_type;
    }

    public String getPaper_info() {
        return paper_info;
    }

    public void setPaper_info(String paper_info) {
        this.paper_info = paper_info;
    }

    public String getOther_require() {
        return other_require;
    }

    public void setOther_require(String other_require) {
        this.other_require = other_require;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Assign> getAssign() {
        return assign;
    }

    public void setAssign(List<Assign> assign) {
        this.assign = assign;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
