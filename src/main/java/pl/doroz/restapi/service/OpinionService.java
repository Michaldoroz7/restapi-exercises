package pl.doroz.restapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.doroz.restapi.entity.Opinion;
import pl.doroz.restapi.handler.OpinionNotFoundException;
import pl.doroz.restapi.repository.OpinionRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    private static final Function<Long, Supplier<OpinionNotFoundException>> OPINION_NOT_FOUND_EXCEPTION = (id) -> () -> new OpinionNotFoundException("Opinion not found with id: " + id);

    public List<Opinion> getAllOpinions() {
        return opinionRepository.findAll();
    }

    public List<Opinion> getEmployeeOpinions(Long id) {
        return opinionRepository.findAll()
                .stream()
                .filter(opinion -> opinion.getAboutEmployee().getId().equals(id))
                .toList();
    }

    public Optional<Opinion> getOpinionById(Long id) {
        return opinionRepository.findById(id);
    }

    public Opinion createOpinion(Opinion opinion) {
        Long id = (long) (opinionRepository.findAll().size() + 1);
        opinionRepository.save(opinion);

        return opinion;
    }

    public void removeOpinion(Long id) {
        opinionRepository.findById(id).orElseThrow(OPINION_NOT_FOUND_EXCEPTION.apply(id));
        opinionRepository.deleteById(id);
    }

    public void updateOpinion(Long id, Opinion opinion) {
        Opinion existingOpinion = opinionRepository.findById(id).orElseThrow(OPINION_NOT_FOUND_EXCEPTION.apply(id));

        BeanUtils.copyProperties(opinion, existingOpinion, "id");
        opinionRepository.save(existingOpinion);

    }
}
