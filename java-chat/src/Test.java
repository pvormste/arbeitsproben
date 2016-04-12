import java.util.ArrayList;

import de.semfer.database.Config;
import de.semfer.database.GeneralInfo;
import de.semfer.database.InsertDB;
import de.semfer.database.QueryDB;
import de.semfer.database.User;

public class Test {


	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Config cfg = new Config();
		cfg.createTables("bauminator");
		
		GeneralInfo gInfo = new GeneralInfo("TestSeite","0.1");
		User hans = new User("Hans111", "111", "hans@hans.de", "Peter", "Wutzke");
		
		InsertDB insert = new InsertDB(cfg);
		insert.insertObj(gInfo);
		insert.insertObj(hans);
		
		QueryDB query = new QueryDB(cfg,insert.getFactory());
		ArrayList<Object> result = query.selectSQL("Select user.email from User user",true);
		for (int i=0; i<result.size();i++){
			Object row = result.get(i);
			System.out.println("E-Mail: "+row);
		}
		
		Object[] info = cfg.selectGeneralInfo();
		System.out.println("Seitenname: "+info[0]);
		System.out.println("Version: "+info[1]+"\n");
		
		cfg.updateGeneralInfo("0.2", "bauminator");
		info = cfg.getInfo();
		System.out.println("Neue Version: "+info[1]+"\n");
		
		cfg.updateGeneralInfo("NeuerSeitenname", "0.3", "bauminator");
		info = cfg.getInfo();
		System.out.println("\nSeitenname: "+info[0]);
		System.out.println("Version: "+info[1]);
	}

}
