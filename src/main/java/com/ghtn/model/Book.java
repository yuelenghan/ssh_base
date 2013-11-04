package com.ghtn.model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-4
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Indexed
public class Book implements Serializable {
    private Long id;
    private String name;
    private String content;

    @Id
    @GeneratedValue
    @DocumentId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(impl = StandardAnalyzer.class))
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
