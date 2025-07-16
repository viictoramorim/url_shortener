import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.urlshortener.ShortUrl;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findByShortCode(String shortCode);   
}
