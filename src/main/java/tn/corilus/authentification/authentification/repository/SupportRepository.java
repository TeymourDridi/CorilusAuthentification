package tn.corilus.authentification.authentification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.corilus.authentification.authentification.entities.Support;

public interface SupportRepository extends JpaRepository<Support,Long> {
    @Query("SELECT s FROM Support s WHERE SIZE(s.conversations) = (SELECT MIN(SIZE(sp.conversations)) FROM Support sp)")
    Support findSupportWithLeastConversations();

}
