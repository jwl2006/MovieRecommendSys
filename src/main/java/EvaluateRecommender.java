import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import java.io.File;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;

/**
 * Created by wanghao on 3/30/16.
 */
public class EvaluateRecommender {
    public static void main(String[] args) {
        try {

            DataModel model = new FileDataModel(new File("output.csv"));
            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            RecommenderBuilder Userbuilder = new MyUserRecommenderBuilder();
            double Userresult = evaluator.evaluate(Userbuilder, null, model, 0.8, 1.0);
            System.out.println("User Recommender Evaluate: " + Userresult);

            RecommenderBuilder Itembuilder = new MyItemRecommenderBuilder();
            double Itemresult = evaluator.evaluate(Itembuilder, null, model, 0.8, 1.0);
            System.out.println("Item Recommender Evaluate: " + Itemresult);

        }  catch (Exception e) {
            System.out.println("There was an error.");
            e.printStackTrace();
        }

    }

    public static class MyUserRecommenderBuilder implements RecommenderBuilder {
        public Recommender buildRecommender(DataModel dataModel) throws TasteException {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);
            return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        }
    }

        public static class MyItemRecommenderBuilder implements RecommenderBuilder {
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);

                return new GenericItemBasedRecommender(dataModel, similarity);
            }
    }

}

