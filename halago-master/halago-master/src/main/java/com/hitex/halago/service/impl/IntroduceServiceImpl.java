package com.hitex.halago.service.impl;

import com.hitex.halago.config.Constant;
import com.hitex.halago.model.DAO.introduce.*;
import com.hitex.halago.model.Introduce;
import com.hitex.halago.repository.IntroduceRepository;
import com.hitex.halago.service.IntroduceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.hitex.halago.utils.StringUtils.getSeoImg;

@Service
public class IntroduceServiceImpl implements IntroduceService {
    Logger logger = LoggerFactory.getLogger(IntroduceServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    IntroduceRepository introduceRepository;

    @Override
    public IntroduceDao getListIntroduce(String language) {
        IntroduceDao introduceDaos = new IntroduceDao();
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Select intro.id, intro.title , lang.title as title_en, intro.description, lang.description as description_en, intro.img,   ");
            builder.append("intro.status, intro.content, intro.contentFirst, intro.contentSecond, lang.content as content_en, lang.contentFirst as content_first_en,  ");
            builder.append("lang.contentSecond as content_second_en, intro.imgSecond, intro.imgThird, intro.imgFour, intro.imgFive, intro.type,  ");
            builder.append("intro.totalInfluencer, intro.totalKols, intro.totalStar, intro.imgSix, intro.imgSeven, intro.imgEight, ");
            builder.append("intro.imgNine, intro.imgTen, intro.imgEleven,intro.imgTwelve  ");
            builder.append("from Introduce intro,IntroduceLanguage lang  ");
            builder.append("where intro.id=lang.idIntroduce and lang.languageKey=:languageKey GROUP BY id_introduce ");
            Query query = entityManager.createQuery(builder.toString());
            query.setParameter("languageKey", language);
            List<IntroduceLanguage> results = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            for (Object[] object : objects) {
                IntroduceLanguage introduce = new IntroduceLanguage();
                introduce.setId((Integer) object[0]);
                introduce.setTitle(String.valueOf(object[1]));
                introduce.setTitle_en(String.valueOf(object[2]));
                introduce.setDescription(String.valueOf(object[3]));
                introduce.setDescription_en(String.valueOf(object[4]));
                introduce.setImg(String.valueOf(object[5]));
                introduce.setStatus((Integer) object[6]);
                introduce.setContent(String.valueOf(object[7]));
                introduce.setContentFirst(String.valueOf(object[8]));
                introduce.setContentSecond(String.valueOf(object[9]));
                introduce.setContent_en(String.valueOf(object[10]));
                introduce.setContentFirst_en(String.valueOf(object[11]));
                introduce.setContentSecond_en(String.valueOf(object[12]));
                introduce.setImgSecond(String.valueOf(object[13]));
                introduce.setImgThird(String.valueOf(object[14]));
                introduce.setImgFour(String.valueOf(object[15]));
                introduce.setImgFive(String.valueOf(object[16]));
                introduce.setType((Integer) object[17]);
                introduce.setTotalInfluencer(String.valueOf(object[18]));
                introduce.setTotalKols(String.valueOf(object[19]));
                introduce.setTotalStar(String.valueOf(object[20]));
                introduce.setImgSix(String.valueOf(object[21]));
                introduce.setImgSeven(String.valueOf(object[22]));
                introduce.setImgEight(String.valueOf(object[23]));
                introduce.setImgNine(String.valueOf(object[24]));
                introduce.setImgTen(String.valueOf(object[25]));
                introduce.setImgEleven(String.valueOf(object[26]));
                introduce.setImgTwelve(String.valueOf(object[27]));
                results.add(introduce);
            }
            List<IntroduceBody> introduceBodyList = new ArrayList<>();
            List<IntroduceReason> introduceReasonList = new ArrayList<>();


            for (IntroduceLanguage introduce : results) {
                if (Constant.HEADER == introduce.getType()) {
                    IntroduceHeader introduceHeader = new IntroduceHeader();
                    SeoImg seoImg = getSeoImg(introduce.getImg());
                    SeoImg seoImgSecond = getSeoImg(introduce.getImgSecond());
                    SeoImg seoImgThird = getSeoImg(introduce.getImgThird());
                    SeoImg seoImgFour = getSeoImg(introduce.getImgFour());
                    SeoImg seoImgFive = getSeoImg(introduce.getImgFive());

                    introduceHeader.setImg(seoImg);
                    introduceHeader.setImgSecond(seoImgSecond);
                    introduceHeader.setImgThird(seoImgThird);
                    introduceHeader.setImgFour(seoImgFour);
                    introduceHeader.setImgFive(seoImgFive);
                    BeanUtils.copyProperties(introduce, introduceHeader);
                    if (introduceHeader.getStatus() == 1) {
                        introduceHeader.setStatusName("Active");
                    } else {
                        introduceHeader.setStatusName("InActive");
                    }
                    introduceHeader.setTypeName("header");
                    introduceDaos.setHeader(new Header(introduceHeader));
                } else if (Constant.BODY == introduce.getType()) {
                    IntroduceBody introduceBody = new IntroduceBody();
                    BeanUtils.copyProperties(introduce, introduceBody);
                    SeoImg seoImg = getSeoImg(introduce.getImg());
                    introduceBody.setImg(seoImg);
                    if (introduceBody.getStatus() == 1) {
                        introduceBody.setStatusName("Active");
                    } else {
                        introduceBody.setStatusName("InActive");
                    }
                    introduceBody.setTypeName("body");
                    introduceBodyList.add(introduceBody);
                } else if (Constant.REASON == introduce.getType()) {
                    IntroduceReason introduceReason = new IntroduceReason();
                    BeanUtils.copyProperties(introduce, introduceReason);
                    if (introduceReason.getStatus() == 1) {
                        introduceReason.setStatusName("Active");
                    } else {
                        introduceReason.setStatusName("InActive");
                    }
                    introduceReason.setTypeName("reason");
                    introduceReasonList.add(introduceReason);
                } else if (Constant.FOOTER == introduce.getType()) {
                    IntroduceFooter introduceFooter = new IntroduceFooter();
                    BeanUtils.copyProperties(introduce, introduceFooter);
                    SeoImg seoImg = getSeoImg(introduce.getImg());
                    introduceFooter.setImg(seoImg);
                    if (introduceFooter.getStatus() == 1) {
                        introduceFooter.setStatusName("Active");
                    } else {
                        introduceFooter.setStatusName("InActive");
                    }
                    introduceFooter.setTypeName("footer");
                    introduceDaos.setFooter(new Footer(introduceFooter));
                }else if(Constant.BRAND_DEPLOYMENT == introduce.getType()){
                    IntroduceBrandDeployment introduceBrandDeployment = new IntroduceBrandDeployment();
                    BeanUtils.copyProperties(introduce, introduceBrandDeployment);
                    SeoImg seoImg = getSeoImg(introduce.getImg());
                    SeoImg seoImgSecond = getSeoImg(introduce.getImgSecond());
                    SeoImg seoImgThird = getSeoImg(introduce.getImgThird());
                    SeoImg seoImgFour = getSeoImg(introduce.getImgFour());
                    SeoImg seoImgFive = getSeoImg(introduce.getImgFive());
                    SeoImg seoImgSix = getSeoImg(introduce.getImgSix());
                    SeoImg seoImgSeven = getSeoImg(introduce.getImgSeven());
                    SeoImg seoImgEight = getSeoImg(introduce.getImgEight());
                    SeoImg seoImgNine = getSeoImg(introduce.getImgNine());
                    SeoImg seoImgTen = getSeoImg(introduce.getImgTen());
                    SeoImg seoImgEleven = getSeoImg(introduce.getImgEleven());
                    SeoImg seoImgTwelve = getSeoImg(introduce.getImgTwelve());

                    introduceBrandDeployment.setImg(seoImg);
                    introduceBrandDeployment.setImgSecond(seoImgSecond);
                    introduceBrandDeployment.setImgThird(seoImgThird);
                    introduceBrandDeployment.setImgFour(seoImgFour);
                    introduceBrandDeployment.setImgFive(seoImgFive);
                    introduceBrandDeployment.setImgSix(seoImgSix);
                    introduceBrandDeployment.setImgSeven(seoImgSeven);
                    introduceBrandDeployment.setImgEight(seoImgEight);
                    introduceBrandDeployment.setImgNine(seoImgNine);
                    introduceBrandDeployment.setImgTen(seoImgTen);
                    introduceBrandDeployment.setImgEleven(seoImgEleven);
                    introduceBrandDeployment.setImgTwelve(seoImgTwelve);
                    if (introduceBrandDeployment.getStatus() == 1) {
                        introduceBrandDeployment.setStatusName("Active");
                    } else {
                        introduceBrandDeployment.setStatusName("InActive");
                    }
                    introduceBrandDeployment.setTypeName("brandDeployment");
                    introduceDaos.setBrandDeployment(new BrandDeployment(introduceBrandDeployment));
                } else{
                    JoinUs joinUs = new JoinUs();
                    BeanUtils.copyProperties(introduce, joinUs);
                    joinUs.setTitle_En(introduce.getTitle_en());
                    joinUs.setContent_En(introduce.getContent_en());
                    if (joinUs.getStatus() == 1) {
                        joinUs.setStatusName("Active");
                    } else {
                        joinUs.setStatusName("InActive");
                    }
                    joinUs.setTypeName("JoinUs");
                    introduceDaos.setJoinUs(joinUs);
                }
            }
            introduceDaos.setBody(new Body(introduceBodyList));
            introduceDaos.setReason(new Reason(introduceReasonList));
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return introduceDaos;
    }



    @Override
    public IntroduceDao getListIntroducePortal(String language) {
        IntroduceDao introduceDaos = new IntroduceDao();
        try {
            List<IntroduceBody> introduceBodyList = new ArrayList<>();
            List<IntroduceReason> introduceReasonList = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            if (Constant.LANGUAGE_VN.equals(language)) {
                builder.append("Select intro ");
                builder.append("from Introduce intro  ");
                Query query = entityManager.createQuery(builder.toString(),Introduce.class);
                List<Introduce> results = query.getResultList();
                setLanguge(introduceDaos, introduceBodyList, introduceReasonList, results);
            }else{
                builder.append("Select intro.id, lang.title, lang.description, intro.img, intro.status, lang.content, lang.contentFirst, lang.contentSecond,  ");
                builder.append("intro.imgSecond, intro.imgThird, intro.imgFour, intro.imgFive, intro.type, intro.totalInfluencer, intro.totalKols, intro.totalStar,  ");
                builder.append("intro.imgSix, intro.imgSeven, intro.imgEight,  ");
                builder.append("intro.imgNine, intro.imgTen, intro.imgEleven,intro.imgTwelve  ");
                builder.append("from Introduce intro,IntroduceLanguage lang  ");
                builder.append("where intro.id=lang.idIntroduce and lang.languageKey =:languageKey ");
                Query query = entityManager.createQuery(builder.toString());
                query.setParameter("languageKey",language);
                List<Introduce> results = new ArrayList<>();
                List<Object[]> objects = query.getResultList();
                getValueObject(results, objects);
                setLanguge(introduceDaos, introduceBodyList, introduceReasonList, results);
            }
            introduceDaos.setBody(new Body(introduceBodyList));
            introduceDaos.setReason(new Reason(introduceReasonList));
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage(), e);
        }
        return introduceDaos;
    }

    private void getValueObject(List<Introduce> results, List<Object[]> objects) {
        for (Object[] object :objects) {
            Introduce introduce = new Introduce();
            introduce.setId((Integer) object[0]);
            introduce.setTitle(String.valueOf(object[1]));
            introduce.setDescription(String.valueOf(object[2]));
            introduce.setImg(String.valueOf(object[3]));
            introduce.setStatus((Integer) object[4]);
            introduce.setContent(String.valueOf(object[5]));
            introduce.setContentFirst(String.valueOf(object[6]));
            introduce.setContentSecond(String.valueOf(object[7]));
            introduce.setImgSecond(String.valueOf(object[8]));
            introduce.setImgThird(String.valueOf(object[9]));
            introduce.setImgFour(String.valueOf(object[10]));
            introduce.setImgFive(String.valueOf(object[11]));
            introduce.setType((Integer) object[12]);
            introduce.setTotalInfluencer(String.valueOf(object[13]));
            introduce.setTotalKols(String.valueOf(object[14]));
            introduce.setTotalStar(String.valueOf(object[15]));
            introduce.setImgSix(String.valueOf(object[16]));
            introduce.setImgSeven(String.valueOf(object[17]));
            introduce.setImgEight(String.valueOf(object[18]));
            introduce.setImgNine(String.valueOf(object[19]));
            introduce.setImgTen(String.valueOf(object[20]));
            introduce.setImgEleven(String.valueOf(object[21]));
            introduce.setImgTwelve(String.valueOf(object[22]));
            results.add(introduce);
        }
    }

    private void setLanguge(IntroduceDao introduceDaos, List<IntroduceBody> introduceBodyList, List<IntroduceReason> introduceReasonList, List<Introduce> results) {
        for (Introduce introduce : results) {
            if (Constant.HEADER == introduce.getType()) {
                setIntroduceHeader(introduceDaos, introduce);
            } else if (Constant.BODY == introduce.getType()) {
                IntroduceBody introduceBody = new IntroduceBody();
                BeanUtils.copyProperties(introduce, introduceBody);
                SeoImg seoImg = getSeoImg(introduce.getImg());
                introduceBody.setImg(seoImg);
                if (introduceBody.getStatus() == 1) {
                    introduceBody.setStatusName("Active");
                } else {
                    introduceBody.setStatusName("InActive");
                }
                introduceBody.setTypeName("body");
                introduceBodyList.add(introduceBody);
            } else if (Constant.REASON == introduce.getType()) {
                IntroduceReason introduceReason = new IntroduceReason();
                SeoImg seoImg = getSeoImg(introduce.getImg());
                introduceReason.setImg(seoImg);
                BeanUtils.copyProperties(introduce, introduceReason);
                if (introduceReason.getStatus() == 1) {
                    introduceReason.setStatusName("Active");
                } else {
                    introduceReason.setStatusName("InActive");
                }
                introduceReason.setTypeName("reason");
                introduceReasonList.add(introduceReason);
            } else if (Constant.FOOTER == introduce.getType()) {
                IntroduceFooter introduceFooter = new IntroduceFooter();
                BeanUtils.copyProperties(introduce, introduceFooter);
                SeoImg seoImg = getSeoImg(introduce.getImg());
                introduceFooter.setImg(seoImg);
                if (introduceFooter.getStatus() == 1) {
                    introduceFooter.setStatusName("Active");
                } else {
                    introduceFooter.setStatusName("InActive");
                }
                introduceFooter.setTypeName("footer");
                introduceDaos.setFooter(new Footer(introduceFooter));
            } else if(Constant.BRAND_DEPLOYMENT == introduce.getType()){
                IntroduceBrandDeployment introduceBrandDeployment = new IntroduceBrandDeployment();
                BeanUtils.copyProperties(introduce, introduceBrandDeployment);
                SeoImg seoImg = getSeoImg(introduce.getImg());
                SeoImg seoImgSecond = getSeoImg(introduce.getImgSecond());
                SeoImg seoImgThird = getSeoImg(introduce.getImgThird());
                SeoImg seoImgFour = getSeoImg(introduce.getImgFour());
                SeoImg seoImgFive = getSeoImg(introduce.getImgFive());
                SeoImg seoImgSix = getSeoImg(introduce.getImgSix());
                SeoImg seoImgSeven = getSeoImg(introduce.getImgSeven());
                SeoImg seoImgEight = getSeoImg(introduce.getImgEight());
                SeoImg seoImgNine = getSeoImg(introduce.getImgNine());
                SeoImg seoImgTen = getSeoImg(introduce.getImgTen());
                SeoImg seoImgEleven = getSeoImg(introduce.getImgEleven());
                SeoImg seoImgTwelve = getSeoImg(introduce.getImgTwelve());

                introduceBrandDeployment.setImg(seoImg);
                introduceBrandDeployment.setImgSecond(seoImgSecond);
                introduceBrandDeployment.setImgThird(seoImgThird);
                introduceBrandDeployment.setImgFour(seoImgFour);
                introduceBrandDeployment.setImgFive(seoImgFive);
                introduceBrandDeployment.setImgSix(seoImgSix);
                introduceBrandDeployment.setImgSeven(seoImgSeven);
                introduceBrandDeployment.setImgEight(seoImgEight);
                introduceBrandDeployment.setImgNine(seoImgNine);
                introduceBrandDeployment.setImgTen(seoImgTen);
                introduceBrandDeployment.setImgEleven(seoImgEleven);
                introduceBrandDeployment.setImgTwelve(seoImgTwelve);
                if (introduceBrandDeployment.getStatus() == 1) {
                    introduceBrandDeployment.setStatusName("Active");
                } else {
                    introduceBrandDeployment.setStatusName("InActive");
                }
                introduceBrandDeployment.setTypeName("brandDeployment");
                introduceDaos.setBrandDeployment(new BrandDeployment(introduceBrandDeployment));
            } else{
                JoinUs joinUs = new JoinUs();
                BeanUtils.copyProperties(introduce, joinUs);
                if (joinUs.getStatus() == 1) {
                    joinUs.setStatusName("Active");
                } else {
                    joinUs.setStatusName("InActive");
                }
                joinUs.setTypeName("JoinUs");
                introduceDaos.setJoinUs(joinUs);
            }
        }
    }

    private void setIntroduceHeader(IntroduceDao introduceDaos, Introduce introduce) {
        IntroduceHeader introduceHeader = new IntroduceHeader();
        SeoImg seoImg = getSeoImg(introduce.getImg());
        SeoImg seoImgSecond = getSeoImg(introduce.getImgSecond());
        SeoImg seoImgThird = getSeoImg(introduce.getImgThird());
        SeoImg seoImgFour = getSeoImg(introduce.getImgFour());
        SeoImg seoImgFive = getSeoImg(introduce.getImgFive());

        introduceHeader.setImg(seoImg);
        introduceHeader.setImgSecond(seoImgSecond);
        introduceHeader.setImgThird(seoImgThird);
        introduceHeader.setImgFour(seoImgFour);
        introduceHeader.setImgFive(seoImgFive);
        BeanUtils.copyProperties(introduce, introduceHeader);
        if (introduceHeader.getStatus() == 1) {
            introduceHeader.setStatusName("Active");
        } else {
            introduceHeader.setStatusName("InActive");
        }
        introduceHeader.setTypeName("header");
        introduceDaos.setHeader(new Header(introduceHeader));
    }

}
