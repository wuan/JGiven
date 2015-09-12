package com.tngtech.jgiven.examples.attachments;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.io.BaseEncoding;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.attachment.MediaType;

public class AttachmentsExampleStage extends Stage<AttachmentsExampleStage> {

    @ExpectedScenarioState
    CurrentStep currentStep;

    private String content;

    public void some_text_content( @Quoted String content ) {
        this.content = content;
    }

    public void it_can_be_added_as_an_attachment_to_the_step_with_title( String title ) {
        currentStep.addAttachment( Attachment.plainText( content )
            .withTitle( title ) );

    }

    public void a_large_oval_circle() throws IOException {
        drawOval( 800, 600 );
    }

    public void an_oval_circle() throws IOException {
        drawOval( 300, 200 );
    }

    private void drawOval( int width, int height ) throws IOException {
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );

        Graphics2D g = image.createGraphics();
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON );

        g.setStroke( new BasicStroke( 10 ) );
        g.setPaint( Color.BLUE );
        g.drawOval( 10, 10, width - 20, height - 20 );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write( image, "PNG", outputStream );
        String base64String = BaseEncoding.base64().encode( outputStream.toByteArray() );

        currentStep.addAttachment(
            Attachment.fromBase64( base64String, MediaType.PNG )
                .withTitle( "An oval drawn in Java" )
                .showDirectly() );
    }
}
