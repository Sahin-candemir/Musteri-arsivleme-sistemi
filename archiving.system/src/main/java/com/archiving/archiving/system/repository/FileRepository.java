package com.archiving.archiving.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archiving.archiving.system.model.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
