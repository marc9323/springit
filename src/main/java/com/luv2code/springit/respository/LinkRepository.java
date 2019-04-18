package com.luv2code.springit.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luv2code.springit.domain.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{
}
