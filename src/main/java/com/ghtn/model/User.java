package com.ghtn.model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-1
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Indexed
@Audited
public class User implements Serializable {
    private Long id;
    private String name;
    private int age;

    @Id
    @GeneratedValue
    @DocumentId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Length(max = 10)
    @Field(index = Index.YES, store = Store.YES, analyze = Analyze.YES, analyzer = @Analyzer(impl = StandardAnalyzer.class))
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
