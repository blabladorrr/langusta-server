package pl.edu.pjwstk.langustaserver.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.langustaserver.model.RecipeCollection;
import pl.edu.pjwstk.langustaserver.repository.RecipeCollectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeCollectionService {
    private final RecipeCollectionRepository recipeCollectionRepository;
    private final SessionFactory hibernateFactory;

    public RecipeCollectionService(RecipeCollectionRepository recipeCollectionRepository,
                                   SessionFactory hibernateFactory) {
        this.recipeCollectionRepository = recipeCollectionRepository;

        if (hibernateFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Factory is not a hibernate factory");
        }

        this.hibernateFactory = hibernateFactory;
    }

    private RecipeCollection fetchRecipeCollectionById(UUID id) {
        Session session = hibernateFactory.openSession();

        RecipeCollection foundCollection = (RecipeCollection) session.find(RecipeCollection.class, id);

        if (foundCollection != null) {
            Hibernate.initialize(foundCollection.getRecipes());
        }

        session.close();

        return foundCollection;
    }

    public List<RecipeCollection> fetchRecipeCollectionsByIds(List<UUID> ids) {
        List<RecipeCollection> collectionsList = new ArrayList<>();

        ids.forEach(id -> collectionsList.add(fetchRecipeCollectionById(id)));

//        return recipeCollectionRepository.findAllById(ids);
        return collectionsList;
    }

    public List<RecipeCollection> uploadRecipeCollections(List<RecipeCollection> collectionsToSave) {
        recipeCollectionRepository.saveAll(collectionsToSave);
        return collectionsToSave;
    }

    public List<UUID> deleteRecipeCollectionsByIds(List<UUID> ids) {
        recipeCollectionRepository.deleteByIdIn(ids);
        return ids;
    }
}