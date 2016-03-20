package rest.resources;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import javax.ws.rs.POST;

/**
 * Created by Kubus on 2016-03-19.
 */
@Path("/fileservice")
public class FileController {

    private static final String PATH  = "C:\\Users\\Kubus\\Desktop\\Test zdjecia\\";

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImageFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileFormDataContentDisposition) {

        InputStream f1 = fileInputStream;


        // local variables
        String fileName = null;
        String uploadFilePath = null;

        try {
            fileName = fileFormDataContentDisposition.getFileName();
            fileName = fileName;
            writeToFileServer(f1, fileName,0);
            /*
            for(int i =1;i <=3;i++){
                writeToFileServer(f1, fileName,i);
            }
            */
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            // release resour
            // ces, if any
        }

    }

    private InputStream writeToFileServer(InputStream inputStream, String fileName,int i) throws IOException {

        OutputStream outputStream = null;
        String qualifiedUploadFilePath = PATH + fileName.substring(0,fileName.indexOf('.'));
        if(i==0){
            qualifiedUploadFilePath=qualifiedUploadFilePath + ".jpg_new";
        }
        /*else if(i==1){
            qualifiedUploadFilePath=qualifiedUploadFilePath + "_ths.jpg";
        }else if(i==2){
            qualifiedUploadFilePath=qualifiedUploadFilePath + "_thm.jpg";
        }else if(i==3){
            qualifiedUploadFilePath=qualifiedUploadFilePath + "_thb.jpg";
        }
*/
        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally{
            //release resource, if any
            outputStream.close();
        }
        return inputStream;
    }


}
