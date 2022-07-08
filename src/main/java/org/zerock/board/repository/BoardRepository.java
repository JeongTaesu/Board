package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Borad;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Borad, Long> , SearchBoardRepository {

    @Query ("select b, w from Borad b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("SELECT b, r FROM Borad b LEFT JOIN Reply r ON r.borad = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value ="SELECT b, w, count(r) " +
            " FROM Borad b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.borad = b " +
            " GROUP BY b",
            countQuery ="SELECT count(b) FROM Borad b")
    Page<Object[]> getBoardWithReplyConunt(Pageable pageable);

    @Query("SELECT b, w, count(r) " +
            " FROM Borad b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.borad = b" +
            " WHERE b.bno = :bno")
    Object getBoradByBno(@Param("bno") Long bno);
}
