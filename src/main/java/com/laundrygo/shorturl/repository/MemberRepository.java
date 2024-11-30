package com.laundrygo.shorturl.repository;

import com.laundrygo.shorturl.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
