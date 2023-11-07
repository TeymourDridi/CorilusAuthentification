package tn.corilus.authentification.authentification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.corilus.authentification.authentification.entities.Support;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support,Long> {

    @Query("SELECT s FROM Support s WHERE SIZE(s.conversations) = (SELECT MIN(SIZE(sp.conversations)) FROM Support sp)")
    List<Support> findSupportWithLeastConversations();
    @Query("SELECT s FROM Support s WHERE SIZE(s.conversations) = (SELECT MIN(SIZE(sp.conversations))  FROM Support sp) and s.id <> :id")
    List<Support> findSupportWithLeastConversations(@Param("id") Long id);

}
