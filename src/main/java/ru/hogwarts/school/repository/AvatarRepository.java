package ru.hogwarts.school.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;

@Repository
public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {
    Avatar findByStudentId(long studentId);

}
