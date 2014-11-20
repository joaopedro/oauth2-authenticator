package org.smartech.authenticator.core;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * This is a abstract class that contains normal code for all domain objects
 * that have a single key. It already has the id and implements toString, equals and
 * hashcode.
 *
 * User: cneto
 * Date: 2014/10/30
 */
@MappedSuperclass
public abstract class SimplePersistentObject implements IPersistent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof SimplePersistentObject) {
            SimplePersistentObject simpleObj = (SimplePersistentObject) obj;
            return this.id == simpleObj.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).toString();
    }

}
