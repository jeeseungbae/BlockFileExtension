package com.example.exam.persistance;

import com.example.exam.domain.FixedExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface FixedExtensionRepository extends JpaRepository<FixedExtension, Long> {

    @Modifying
    @Query("update FixedExtension as fix set fix.bat=?1 where fix.seq=1")
    void updateBat(boolean bat);

    @Modifying
    @Query("update FixedExtension as fix set fix.cmd=?1 where fix.seq=1")
    void updateCmd(boolean cmd);

    @Modifying
    @Query("update FixedExtension as fix set fix.com=?1 where fix.seq=1")
    void updateCom(boolean com);

    @Modifying
    @Query("update FixedExtension as fix set fix.cpl=?1 where fix.seq=1")
    void updateCpl(boolean cpl);

    @Modifying
    @Query("update FixedExtension as fix set fix.exe=?1 where fix.seq=1")
    void updateExe(boolean exe);

    @Modifying
    @Query("update FixedExtension as fix set fix.scr=?1 where fix.seq=1")
    void updateScr(boolean scr);

    @Modifying
    @Query("update FixedExtension as fix set fix.js=?1 where fix.seq=1")
    void updateJs(boolean js);

    Optional<FixedExtension> findBySeq(Long seq);
}
