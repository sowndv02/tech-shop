package sondv.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sondv.shop.domain.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    
}
