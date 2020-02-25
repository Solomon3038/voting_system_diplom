package com.voting.system.project.util;

import com.voting.system.project.model.HasId;
import com.voting.system.project.util.exception.IllegalRequestDataException;
import com.voting.system.project.util.exception.NotExistException;
import com.voting.system.project.util.exception.VoteException;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidationUtil {
    public static final LocalTime VOTE_MAX_TIME = LocalTime.of(11, 00, 00);

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

    public static void checkDate(LocalDate date) {
        if (date.compareTo(LocalDate.now()) != 0) {
            throw new VoteException("vote date " + date + " must be equal current date");
        }
    }

    public static void checkTime() {
        if (LocalTime.now().compareTo(VOTE_MAX_TIME) > 0) {
            throw new VoteException("vote can't be accepted after " + VOTE_MAX_TIME + "AM");
        }
    }
}