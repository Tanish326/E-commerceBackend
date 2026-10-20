package com.e_commerceapp.E_Commerce.Repository;
import com.e_commerceapp.E_Commerce.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository 
public interface UserRepository extends JpaRepository<Users,Long> {

    public Optional<Users> findByusername(String username);
     public  Boolean existsByusername(String username);
     public Boolean existsByemail(String email);
   
}
