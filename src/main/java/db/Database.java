package db;

import com.google.common.collect.Maps;

import model.Board;
import model.User;
import util.Session;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public class Database {
    private static long boardId = 1;
    private static Map<String, User> users = Maps.newHashMap();
    private static Map<String, Session> sessoinDB = Maps.newConcurrentMap();
    private static Map<Long, Board> boardDB = Maps.newConcurrentMap();


    // User
    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    // Session
    public static void addSession(Session session) {
        sessoinDB.put(session.getSessionId(), session);
    }

    public static Session getSession(String sessionId) {
        return sessoinDB.get(sessionId);
    }

    // Board
    public static void addBoard(Board board) {
        boardDB.put(boardId++, board);
    }
}
