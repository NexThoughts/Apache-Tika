package com

import org.apache.tika.Tika
import org.apache.tika.language.LanguageIdentifier
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.Parser
import org.apache.tika.parser.asm.ClassParser
import org.apache.tika.parser.html.HtmlParser
import org.apache.tika.sax.BodyContentHandler


class DemoController {

    def index() {
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream inputstream = new FileInputStream(new File("/home/nexthought/Downloads/A.class"))
        ParseContext pcontext = new ParseContext()

        ClassParser ClassParser = new ClassParser()
        ClassParser.parse(inputstream, handler, metadata, pcontext)
        println("Contents of the document:" + handler.toString())
        println("Metadata of the document:")
        String[] metadataNames = metadata.names()

        for (String name : metadataNames) {
            println(name + " :  " + metadata.get(name))
        }


        render "ok"
    }


    def typeDetection() {
        Tika tika = new Tika()
        File file = new File("/home/nexthought/Downloads/01 Badri Ki Dulhania - Title Track.mp3")
        String filetype = tika.detect(file)
        println("For MP3 -> " + filetype)
        file = new File("/home/nexthought/Downloads/A.class")
        filetype = tika.detect(file)
        println("For Class -> " + filetype)
        file = new File("/home/nexthought/Downloads/pdf.pdf")
        filetype = tika.detect(file)
        println("For PDF -> " + filetype)

        render filetype
    }

    def contentExtraction() {
        Tika tika = new Tika()
        File file = new File("/home/nexthought/Downloads/01 Badri Ki Dulhania - Title Track.mp3")
        println("For MP3 -> " + tika.parseToString(file))
//        file = new File("/home/nexthought/Downloads/A.class")
//        println("For Class -> "+tika.parseToString(file))
//        file = new File("/home/nexthought/Downloads/pdf.pdf")
//        println("For PDF -> "+tika.parseToString(file))

        render "success"
    }


    def tikaFacade() {
        Tika tika = new Tika()
        File file = new File("/home/nexthought/Downloads/01 Badri Ki Dulhania - Title Track.mp3")
        String filetype = tika.detect(file)

        println("For MP3 type-> " + filetype)
        println "--------------------------------------------------------------------------------------------------------"
        println("For MP3 Content -> " + tika.parseToString(file))
        println "--------------------------------------------------------------------------------------------------------"
        println("For MP3  Length-> " + tika.getMaxStringLength())


        println "========================================================================================================="

        file = new File("/home/nexthought/Downloads/A.class")
        filetype = tika.detect(file)
        println("For Class type-> " + filetype)
        println "--------------------------------------------------------------------------------------------------------"

        println("For Class Content -> " + tika.parseToString(file))
        println "========================================================================================================="


        file = new File("/home/nexthought/Downloads/pdf.pdf")
        filetype = tika.detect(file)
        println("For PDF type-> " + filetype)
        println "--------------------------------------------------------------------------------------------------------"
        tika.setMaxStringLength(100)
        println("For PDF Content -> " + tika.parseToString(file))
        println "========================================================================================================="

        //        Metadata metadata = new Metadata()
//        tika.parseToString(new FileInputStream(file), metadata)
//        String[] metadataNames = metadata.names()
//
//        for (String name : metadataNames) {
//            println(name + ": " + metadata.get(name))
//        }
        render "success"

    }

    def contentExtractionUsingParseMethod() {
        File file = new File("/home/nexthought/Downloads/pdf.pdf")

        Parser parser = new AutoDetectParser()
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream inputstream = new FileInputStream(file)
        ParseContext context = new ParseContext()

        parser.parse(inputstream, handler, metadata, context)
        println("File content : " + handler.toString())

        render handler.toString()
    }


    def metaDataExample() {
        File file = new File("/home/nexthought/Downloads/pdf.pdf")

        Parser parser = new AutoDetectParser()
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream inputstream = new FileInputStream(file)
        ParseContext context = new ParseContext()

        parser.parse(inputstream, handler, metadata, context)

        String[] metadataNames = metadata.names()

        for (String name : metadataNames) {
            println(name + ": " + metadata.get(name))
        }

//        Adding New Metadata Values
        metadata.add("author", "rohit")
        metadata.add(Metadata.SOFTWARE, "IDEA")
        metadata.set("title", "Apache Tika")

        metadataNames = metadata.names()
        for (String name : metadataNames) {
            println(name + ": " + metadata.get(name))
        }

        //Get Property
        println "Metadata.SOFTWARE : " + metadata.get(Metadata.SOFTWARE)
        println "author : " + metadata.get("author")
        println "title : " + metadata.get("title")
        render "success"

    }


    def languageDetectionExample() {
        LanguageIdentifier languageIdentifier = new LanguageIdentifier("dette er tysk")
        println "german language : " + languageIdentifier.getLanguage()
        languageIdentifier = new LanguageIdentifier("esto es español")
        println "spanish language : " + languageIdentifier.getLanguage()

        //Language Detection from File
        File file = new File("/home/nexthought/Downloads/pdf.pdf")

        Parser parser = new AutoDetectParser()
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream content = new FileInputStream(file)

        parser.parse(content, handler, metadata, new ParseContext())

        languageIdentifier = new LanguageIdentifier(handler.toString())
        println("Language name :" + languageIdentifier.getLanguage())
        render "success"
    }

    def extractingHtmlContent() {
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream inputstream = new FileInputStream(new File("/home/nexthought/Downloads/example.html"))
        ParseContext pcontext = new ParseContext()

        //Html parser
        HtmlParser htmlparser = new HtmlParser()
        htmlparser.parse(inputstream, handler, metadata, pcontext)
        println("Contents of the document:")
        println(handler.toString())
        println("Metadata of the document:")
        String[] metadataNames = metadata.names()

        for (String name : metadataNames) {
            println(name + ":   " + metadata.get(name))
        }
        render "success"
    }

    def extractingPptContent() {
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream inputstream = new FileInputStream(new File("/home/nexthought/Downloads/Introduction_to_Tika.ppt"))
        ParseContext pcontext = new ParseContext()

        //Html parser
        Parser parser = new AutoDetectParser()
        parser.parse(inputstream, handler, metadata, pcontext)
        println("Contents of the document:")
        println(handler.toString())
        println("Metadata of the document:")
        String[] metadataNames = metadata.names()

        for (String name : metadataNames) {
            println(name + ":   " + metadata.get(name))
        }
        render "success"
    }

    def extractingOdpContent() {
        BodyContentHandler handler = new BodyContentHandler()
        Metadata metadata = new Metadata()
        FileInputStream inputstream = new FileInputStream(new File("/home/nexthought/Downloads/untitled_0.odp"))
        ParseContext pcontext = new ParseContext()

        Parser parser = new AutoDetectParser()
        parser.parse(inputstream, handler, metadata, pcontext)
        println("Contents of the document:")
        println(handler.toString())
        println("Metadata of the document:")
        String[] metadataNames = metadata.names()

        for (String name : metadataNames) {
            println(name + ":   " + metadata.get(name))
        }
        render "success"
    }

}
