package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.zerock.board.entity.Borad;
import org.zerock.board.entity.Reply;

import java.util.List;
@EnableJpaRepositories
public interface ReplyRepository  extends  JpaRepository<Reply, Long>{

    @Modifying
    @Query("delete from Reply r where r.borad.bno = :bno")
    void deleteByBno(Long bno);

    List<Reply> getRepliesByBoardOrderByRno(Borad borad);

}
