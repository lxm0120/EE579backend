package com.openface;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class DoOpenFace {
	public String doOpenFace(String userid) {
		// Prints "Hello, World" to the terminal window.
		String s;
		Process p;
		String train_image_path = "/root/training-images/";
		String open_face_path = "/root/openface/";
		String result=null;
		try {


			String align_command = open_face_path+"util/align-dlib.py "+train_image_path+" align outerEyesAndNose "+open_face_path+"aligned-images --size 96";
			System.out.println ("*************");
			System.out.println (align_command);
			System.out.println ("*************");

			p = Runtime.getRuntime().exec(align_command);
			BufferedReader br = new BufferedReader(
		    new InputStreamReader(p.getInputStream()));

				while ((s = br.readLine()) != null)
		    	System.out.println("line: " + s);

				p.waitFor();
				System.out.println ("exit: " + p.exitValue());
				p.destroy();

		} catch (Exception e) {}
			try {

//				5651  ./batch-represent/main.lua -outDir ./generated-embeddings -data ./aligned-images

				String generate_embed_command = open_face_path+"batch-represent/main.lua -outDir "+open_face_path+"generated-embeddings -data "+open_face_path+"aligned-images";
				System.out.println ("*************");
				System.out.println (generate_embed_command);
				System.out.println ("*************");

				p = Runtime.getRuntime().exec(generate_embed_command);
				BufferedReader br = new BufferedReader(
					new InputStreamReader(p.getInputStream()));

					while ((s = br.readLine()) != null)
						System.out.println("line: " + s);

					p.waitFor();
					System.out.println ("exit: " + p.exitValue());
					p.destroy();

			} catch (Exception e) {}

				try {

	        //	./demos/classifier.py train ./generated-embeddings
					String train_classifier_command = open_face_path+"demos/classifier.py train "+open_face_path+"generated-embeddings";
					System.out.println ("*************");
					System.out.println (train_classifier_command);
					System.out.println ("*************");

					p = Runtime.getRuntime().exec(train_classifier_command);
					BufferedReader br = new BufferedReader(
						new InputStreamReader(p.getInputStream()));

						while ((s = br.readLine()) != null)
							System.out.println("line: " + s);

						p.waitFor();
						System.out.println ("exit: " + p.exitValue());
						p.destroy();

				} catch (Exception e) {}

				try {

					//	./demos/classifier.py infer ./generated-embeddings/classifier.pkl /home/usama/Downloads/image
					String image_path = "/root/testing-image/"+userid+"_picture.jpg";
					String detect_image_command = open_face_path+"demos/classifier.py infer "+open_face_path+"generated-embeddings/classifier.pkl "+image_path;
					System.out.println ("*************");
					System.out.println (detect_image_command);
					System.out.println ("*************");

					p = Runtime.getRuntime().exec(detect_image_command);
					BufferedReader br = new BufferedReader(
						new InputStreamReader(p.getInputStream()));

						while ((s = br.readLine()) != null){
	            if ( s.length() > 0 ){
								int ind1 = s.indexOf("Predict");
								if ( ind1 >= 0 ){
									int ind2 = s.indexOf("with");
									String name = s.substring(ind1+8, ind2 );
									String number = s.substring(ind2+5, ind2+6+3);
									System.out.println("name = "+name);
									System.out.println("number = "+number);
									result=name;
									
								}
							}

						}

						p.waitFor();
						System.out.println ("exit: " + p.exitValue());
						p.destroy();
						


				} catch (Exception e) {}


				return result;
		}
	
}
