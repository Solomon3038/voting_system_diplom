package com.voting.system.project.util;

import com.voting.system.project.model.HasId;
import com.voting.system.project.util.exception.IllegalRequestDataException;
import com.voting.system.project.util.exception.NotExistException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotExistWithId(T object, int id) {
        return checkNotExist(object, "id=" + id);
    }

    public static void checkNotExistWithId(boolean exist, int id) {
        checkNotExist(exist, "id=" + id);
    }

    public static <T> T checkNotExist(T object, String msg) {
        checkNotExist(object != null, msg);
        return object;
    }

    public static void checkNotExist(boolean exist, String msg) {
        if (!exist) {
            throw new NotExistException("Not exist entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}