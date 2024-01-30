package service;

import db.Database;
import exception.SourceException;
import model.Board;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ErrorCode;
import util.ParseParams;

import java.util.List;
import java.util.Map;

public class BoardService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Board> findAllBoard() {
        return Database.findAllBoards().stream().toList();
    }

    public void createBoard(User user, ParseParams bodyData) {
        Map<String, String> data = bodyData.getParamMap();
        Board board = new Board();
        for (String key: data.keySet()) {
            Board.setBoard(board, key, data.get(key));
        }
        Database.addBoard(board);
        board.changeEnterTag();
        logger.debug("Board DataBase Size = {}", Database.findAllBoards().size());
        user.addBoard(board);
    }

    public Board findBoard(ParseParams queryParams) {
        Map<String, String> paramMap = queryParams.getParamMap();
        long boardId = Long.parseLong(paramMap.get("boardId"));
        Board board = Database.findBoardById(boardId);
        if (board == null) {
            throw new SourceException(ErrorCode.NOT_EXIST_BOARD);
        }
        return board;
    }
}
