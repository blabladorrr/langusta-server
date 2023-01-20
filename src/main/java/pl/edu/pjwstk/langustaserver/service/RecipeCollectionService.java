package pl.edu.pjwstk.langustaserver.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.langustaserver.component.PublicRecipeCollectionFetcher;
import pl.edu.pjwstk.langustaserver.exception.UserNotFoundException;
import pl.edu.pjwstk.langustaserver.model.RecipeCollection;
import pl.edu.pjwstk.langustaserver.model.User;
import pl.edu.pjwstk.langustaserver.repository.RecipeCollectionRepository;
import pl.edu.pjwstk.langustaserver.repository.UserRepository;

import java.util.*;

@Service
public class RecipeCollectionService {
    private final RecipeCollectionRepository recipeCollectionRepository;
    private final UserRepository userRepository;
    private final SessionFactory hibernateFactory;
    private final PublicRecipeCollectionFetcher publicRecipeCollectionFetcher;

    public RecipeCollectionService(RecipeCollectionRepository recipeCollectionRepository,
                                   UserRepository userRepository, SessionFactory hibernateFactory,
                                   PublicRecipeCollectionFetcher publicRecipeCollectionFetcher) {
        this.recipeCollectionRepository = recipeCollectionRepository;
        this.userRepository = userRepository;

        if (hibernateFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Factory is not a hibernate factory");
        }

        this.hibernateFactory = hibernateFactory;
        this.publicRecipeCollectionFetcher = publicRecipeCollectionFetcher;
    }

    public List<RecipeCollection> getUserCollections() {
        List<RecipeCollection> collectionList;

        collectionList = fetchUserCollections(getCurrentUserId());

        return collectionList;
    }

    private List<RecipeCollection> fetchUserCollections(UUID id) {
        Session session = hibernateFactory.openSession();

        // TODO: change it to CriteriaQuery
        String query = "SELECT * FROM collections where author = :userId";
        NativeQuery<RecipeCollection> nativeQuery = session.createNativeQuery(query, RecipeCollection.class);
        nativeQuery.setParameter("userId", id.toString());

        List<RecipeCollection> collectionList = (List<RecipeCollection>) nativeQuery.getResultList();

        collectionList.forEach(
                recipeCollection -> getUserRecipeCollectionAssociatedDataAndSetIsOwnedFlag(recipeCollection));

        session.close();

        return collectionList;
    }

    private void getUserRecipeCollectionAssociatedDataAndSetIsOwnedFlag(RecipeCollection recipeCollection) {
        recipeCollection.setIsOwned(true);

        Hibernate.initialize(recipeCollection.getRecipeIds());
    }

    public List<RecipeCollection> getRecipeCollectionsById(List<String> idList) {
        List<RecipeCollection> collectionList = new ArrayList<>();

        idList.forEach(id -> collectionList.add(fetchRecipeCollectionById(id)));

        return collectionList;
    }

    private RecipeCollection fetchRecipeCollectionById(String id) {
        Session session = hibernateFactory.openSession();

        RecipeCollection foundCollection = (RecipeCollection) session.find(RecipeCollection.class, UUID.fromString(id));

        if (foundCollection != null) {
            Hibernate.initialize(foundCollection.getRecipeIds());
        }

        session.close();

        return foundCollection;
    }

    public List<RecipeCollection> getPublicRecipeCollections(Map<String, String> filters) {
        List<RecipeCollection> foundRecipeCollections = findPublicRecipeAccordingToFilters(filters);

        return foundRecipeCollections;
    }

    private List<RecipeCollection> findPublicRecipeAccordingToFilters(Map<String, String> filters) {
        List<RecipeCollection> foundRecipeCollections;
        Session session = hibernateFactory.openSession();

        if (filters.isEmpty()) {
            foundRecipeCollections = publicRecipeCollectionFetcher.findAllPublicRecipeCollections(session);
        }
        else {
            foundRecipeCollections =
                    publicRecipeCollectionFetcher.findAllPublicRecipeCollectionsWithFilters(session, filters);
        }

        session.close();

        return foundRecipeCollections;
    }

    public List<RecipeCollection> saveCollections(List<RecipeCollection> collectionsToSave) {
        collectionsToSave.forEach(collection -> setAuthorForCollectionIfEmpty(collection));

        recipeCollectionRepository.saveAll(collectionsToSave);

        return collectionsToSave;
    }

    private void setAuthorForCollectionIfEmpty(RecipeCollection collection) {
        if (collection.getAuthor() == null) {
            collection.setAuthor(getCurrentUserId().toString());
        }
    }

    public List<String> deleteRecipeCollectionsById(List<String> idList) {
        List<String> deletedRecipeCollectionIds = new ArrayList<>();

        idList.forEach(id -> deleteRecipeCollectionIfExists(UUID.fromString(id), deletedRecipeCollectionIds));

        return deletedRecipeCollectionIds;
    }

    private void deleteRecipeCollectionIfExists(UUID id, List<String> deletedRecipeCollectionIds) {
        if (recipeCollectionRepository.existsById(id)) {
            recipeCollectionRepository.deleteById(id);

            deletedRecipeCollectionIds.add(id.toString());
        }
    }

    private UUID getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return getPresentUser(optionalUser, username).getId();
    }

    private User getPresentUser(Optional<User> optionalUser, String username) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UserNotFoundException(username);
        }
    }
}
