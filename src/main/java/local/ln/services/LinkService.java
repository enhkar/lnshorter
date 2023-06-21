package local.ln.services;

import local.ln.entities.LinkEntity;
import local.ln.entities.UserEntity;
import local.ln.repositories.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    @Autowired
    LinkRepository linkRepository;

    private String generateSuffix(String redirectLink) {
        return RandomStringUtils.randomAlphanumeric(5);
    }

    public String add(String redirectLink, UserEntity user) {
        String suffix = generateSuffix(redirectLink);

        LinkEntity link = new LinkEntity(suffix, redirectLink, user);
        linkRepository.save(link);
        return suffix;
    }


    public LinkEntity getLinkBySuffix(String suffix) {
        return linkRepository.findBySuffix(suffix);
    }

    public List<LinkEntity> getLinksByUser(UserEntity user) {
        return linkRepository.findByUser(user);
    }


    public void switchLinkEnable(Long linkId, boolean enable) {
        Optional<LinkEntity> link = linkRepository.findById(linkId);
        if (link.isPresent()) {
            link.get().setEnabled(enable);
            linkRepository.save(link.get());
        }

    }
}
