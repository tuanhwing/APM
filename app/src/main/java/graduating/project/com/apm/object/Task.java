package graduating.project.com.apm.object;

import java.io.Serializable;

/**
 * Created by Tuan on 27/11/2017.
 */

public class Task implements Serializable {
    private int id;
    private String time_require;
    private int status;
    private String time_created;
    private String name;
    private int count;
    private String cover_color;
    private String cover_paper;
    private String size;
    private String paper_color;
    private String paper_type;
    private String bookbinding_type;
    private String copy_count;
    private String copy_color;
    private String copy_paper_type;
    private String other_require;
    private String file;

    public Task() {
    }

    public Task(int id, String time_require, int status, String time_created, String name, int count, String cover_color, String cover_paper, String size, String paper_color, String paper_type, String bookbinding_type, String copy_count, String copy_color, String copy_paper_type, String other_require, String file) {
        this.id = id;
        this.time_require = time_require;
        this.status = status;
        this.time_created = time_created;
        this.name = name;
        this.count = count;
        this.cover_color = cover_color;
        this.cover_paper = cover_paper;
        this.size = size;
        this.paper_color = paper_color;
        this.paper_type = paper_type;
        this.bookbinding_type = bookbinding_type;
        this.copy_count = copy_count;
        this.copy_color = copy_color;
        this.copy_paper_type = copy_paper_type;
        this.other_require = other_require;
        this.file = file;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPaper_color() {
        return paper_color;
    }

    public void setPaper_color(String paper_color) {
        this.paper_color = paper_color;
    }

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }

    public String getBookbinding_type() {
        return bookbinding_type;
    }

    public void setBookbinding_type(String bookbinding_type) {
        this.bookbinding_type = bookbinding_type;
    }

    public String getCopy_count() {
        return copy_count;
    }

    public void setCopy_count(String copy_count) {
        this.copy_count = copy_count;
    }

    public String getCopy_color() {
        return copy_color;
    }

    public void setCopy_color(String copy_color) {
        this.copy_color = copy_color;
    }

    public String getCopy_paper_type() {
        return copy_paper_type;
    }

    public void setCopy_paper_type(String copy_paper_type) {
        this.copy_paper_type = copy_paper_type;
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
}
