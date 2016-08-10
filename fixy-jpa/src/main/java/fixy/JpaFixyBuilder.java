package fixy;

import org.yaml.snakeyaml.introspector.BeanAccess;

import javax.persistence.EntityManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds a Fixy for use with JPA.
 */
public class JpaFixyBuilder {

    private EntityManager entityManager;
    private boolean mergeEntities;
    private String defaultPackage;
    private BeanAccess beanAccess = BeanAccess.DEFAULT;
    private ClassLoader classLoader;
    private String yamlPathRoot;

    /**
     * Creates the builder with a given JPA EntityManager.
     *
     * @param entityManager the JPA EntityManager to use
     */
    public JpaFixyBuilder(EntityManager entityManager) {
        this.entityManager = checkNotNull(entityManager);
    }

    /**
     * <p>Sets the default package for your entities.</p>
     * <p>The default package will be used if you do not provide a package in your Yaml files.</p>
     *
     * @param defaultPackage the default package name
     * @return the JpaFixyBuilder for further configuration
     */
    public JpaFixyBuilder withDefaultPackage(String defaultPackage) {
        this.defaultPackage = checkNotNull(defaultPackage);
        return this;
    }

    /**
     * Enables entity merging instead of persisting.
     *
     * @return the JpaFixyBuilder for further configuration
     */
    public JpaFixyBuilder mergeEntities() {
        this.mergeEntities = true;
        return this;
    }

    /**
     * Enables field access for entities. This allows immutable fields inside entities.
     *
     * @return the JpaFixyBuilder for further configuration
     */
    public JpaFixyBuilder useFieldAccess() {
        this.beanAccess = BeanAccess.FIELD;
        return this;
    }

    /**
     * Enables use of a custom class loader.
     *
     * @return the JpaFixyBuilder for further configuration
     */
    public JpaFixyBuilder withCustomClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public JpaFixyBuilder withYamlPathRoot(String root) {
        this.yamlPathRoot = root;
        return this;
    }

    /**
     * Builds the Fixy using the provided builder configuration.
     *
     * @return the configured Fixy
     */
    public Fixy build() {
        return new CoreFixy(
                new JPAPersister(entityManager, mergeEntities),
                defaultPackage,
                beanAccess,
                classLoader,
                yamlPathRoot);
    }

}
