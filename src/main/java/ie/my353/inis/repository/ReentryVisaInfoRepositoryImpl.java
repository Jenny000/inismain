package ie.my353.inis.repository;


import ie.my353.inis.entity.ReentryVisaInfo;
import ie.my353.inis.utils.JsoupGetReentryVisaInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
@Component
@Slf4j
public class ReentryVisaInfoRepositoryImpl {
    @Autowired
    private ReentryVisaInfoRepository repository;
    @Autowired
    private JsoupGetReentryVisaInfo reentryVisaInfo;

    public ReentryVisaInfo saveReentryVisaInfo(){
        return repository.save(reentryVisaInfo.getReentryVisa());

    }
}
**/