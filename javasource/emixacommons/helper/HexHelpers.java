package emixacommons.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexHelpers {
	/** Simple value object for detected file type. */
    public static final class FileSignature {
        public final String extension;
        public final String mime;
        public FileSignature(String extension, String mime) {
            this.extension = extension;
            this.mime = mime;
        }
        @Override public String toString() { return extension + " (" + mime + ")"; }
    }

    private static final FileSignature BIN = new FileSignature("bin", "application/octet-stream");

    /** Detect file type from bytes using magic numbers + ZIP introspection. */
    public static FileSignature detect(byte[] b) throws IOException {
        if (b == null || b.length == 0) return BIN;

        // ---- ZIP container (OOXML/JAR/APK/IPA/ZIP) ----
        if (startsWith(b, 0x50,0x4B,0x03,0x04) || startsWith(b, 0x50,0x4B,0x05,0x06) || startsWith(b, 0x50,0x4B,0x07,0x08)) {
            Set<String> names = listZipEntries(b, /*limit*/2000);
            if (names.contains("[Content_Types].xml")) {
                if (names.contains("word/document.xml"))
                    return new FileSignature("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                if (names.contains("xl/workbook.xml"))
                    return new FileSignature("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                if (names.contains("ppt/presentation.xml"))
                    return new FileSignature("pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation");
            }
            if (names.contains("META-INF/MANIFEST.MF")) return new FileSignature("jar","application/java-archive");
            if (anyNameStartsWith(names, "AndroidManifest.xml") || names.contains("AndroidManifest.xml"))
                return new FileSignature("apk","application/vnd.android.package-archive");
            if (anyNameStartsWith(names, "Payload/") && names.stream().anyMatch(n -> n.startsWith("Payload/") && n.endsWith(".app/")))
                return new FileSignature("ipa","application/octet-stream");
            return new FileSignature("zip","application/zip");
        }

        // ---- Documents / containers ----
        if (startsWith(b, 0x25,0x50,0x44,0x46)) return new FileSignature("pdf","application/pdf");          // %PDF
        if (startsWith(b, 0xD0,0xCF,0x11,0xE0,0xA1,0xB1,0x1A,0xE1))
            return new FileSignature("ole","application/x-ole-storage"); // legacy DOC/XLS/PPT/MSG
        if (startsWithAscii(b, "{\\rtf")) return new FileSignature("rtf","application/rtf");
        if (looksLikeXml(b)) return new FileSignature("xml","application/xml");
        if (looksLikeJson(b)) return new FileSignature("json","application/json");

        // ---- Images ----
        if (startsWith(b, 0xFF,0xD8,0xFF)) return new FileSignature("jpg","image/jpeg");
        if (startsWith(b, 0x89,0x50,0x4E,0x47,0x0D,0x0A,0x1A,0x0A)) return new FileSignature("png","image/png");
        if (startsWithAscii(b, "GIF8")) return new FileSignature("gif","image/gif");
        if (startsWith(b, 0x42,0x4D)) return new FileSignature("bmp","image/bmp");
        if (startsWith(b, 0x49,0x49,0x2A,0x00) || startsWith(b, 0x4D,0x4D,0x00,0x2A)) return new FileSignature("tif","image/tiff");
        if (startsWith(b, 0x00,0x00,0x01,0x00)) return new FileSignature("ico","image/vnd.microsoft.icon");
        // WebP RIFF ... "WEBP"
        if (startsWithAscii(b, "RIFF") && bytesAtAscii(b, 8, "WEBP")) return new FileSignature("webp","image/webp");
        // HEIF/HEIC/AVIF (ISOBMFF)
        if (bytesAtAscii(b, 4, "ftyp")) {
            if (bytesAtAscii(b, 8, "heic") || bytesAtAscii(b, 8, "heix") || bytesAtAscii(b, 8, "hevc"))
                return new FileSignature("heic","image/heic");
            if (bytesAtAscii(b, 8, "mif1") || bytesAtAscii(b, 8, "msf1"))
                return new FileSignature("heif","image/heif");
            if (bytesAtAscii(b, 8, "avif"))
                return new FileSignature("avif","image/avif");
        }

        // ---- Audio ----
        if (startsWithAscii(b, "ID3") || startsWith(b, 0xFF,0xFB) || startsWith(b, 0xFF,0xF3) || startsWith(b, 0xFF,0xF2))
            return new FileSignature("mp3","audio/mpeg");
        if (startsWithAscii(b, "fLaC")) return new FileSignature("flac","audio/flac");
        if (startsWithAscii(b, "OggS")) return new FileSignature("oga","audio/ogg"); // may also be .ogg/.opus
        if (startsWithAscii(b, "RIFF") && bytesAtAscii(b, 8, "WAVE")) return new FileSignature("wav","audio/wav");
        // AAC ADTS (need 2 bytes)
        if (b.length >= 2 && (b[0] & 0xFF) == 0xFF && ((b[1] & 0xF6) == 0xF0)) return new FileSignature("aac","audio/aac");
        // MIDI
        if (startsWithAscii(b, "MThd")) return new FileSignature("mid","audio/midi");

        // ---- Video ----
        if (bytesAtAscii(b, 4, "ftyp")) {
            if (bytesAtAscii(b, 8, "mp42") || bytesAtAscii(b, 8, "isom") || bytesAtAscii(b, 8, "iso6") ||
                bytesAtAscii(b, 8, "mp41") || bytesAtAscii(b, 8, "MSNV") || bytesAtAscii(b, 8, "3gp"))
                return new FileSignature("mp4","video/mp4");
            if (bytesAtAscii(b, 8, "qt  ")) return new FileSignature("mov","video/quicktime");
        }
        // MKV/WEBM
        if (startsWith(b, 0x1A,0x45,0xDF,0xA3)) return new FileSignature("mkv","video/x-matroska"); // could be webm too
        // AVI
        if (startsWithAscii(b, "RIFF") && bytesAtAscii(b, 8, "AVI ")) return new FileSignature("avi","video/x-msvideo");

        // ---- Archives / compression ----
        if (startsWith(b, 0x37,0x7A,0xBC,0xAF,0x27,0x1C)) return new FileSignature("7z","application/x-7z-compressed");
        if (startsWith(b, 0x52,0x61,0x72,0x21,0x1A,0x07,0x00)) return new FileSignature("rar","application/vnd.rar"); // RAR4
        if (startsWith(b, 0x52,0x61,0x72,0x21,0x1A,0x07,0x01,0x00)) return new FileSignature("rar","application/vnd.rar"); // RAR5
        if (startsWith(b, 0x1F,0x8B)) return new FileSignature("gz","application/gzip");
        if (startsWith(b, 0x42,0x5A,0x68)) return new FileSignature("bz2","application/x-bzip2");
        if (startsWith(b, 0xFD,0x37,0x7A,0x58,0x5A,0x00)) return new FileSignature("xz","application/x-xz");
        // TAR: "ustar" at offset 257
        if (bytesAtAscii(b, 257, "ustar")) return new FileSignature("tar","application/x-tar");
        // ISO9660: "CD001" at 0x8001 (sector 16)
        if (bytesAtAscii(b, 0x8001, "CD001")) return new FileSignature("iso","application/x-iso9660-image");

        // ---- Fonts ----
        if (startsWith(b, 0x00,0x01,0x00,0x00) || startsWithAscii(b, "true")) return new FileSignature("ttf","font/ttf");
        if (startsWithAscii(b, "OTTO")) return new FileSignature("otf","font/otf");
        if (startsWithAscii(b, "wOFF")) return new FileSignature("woff","font/woff");
        if (startsWithAscii(b, "wOF2")) return new FileSignature("woff2","font/woff2");

        // ---- Certificates / Keys ----
        if (startsWithAscii(b, "-----BEGIN ")) return new FileSignature("pem","application/x-pem-file");
        if ((b[0] & 0xFF) == 0x30 && b.length > 4 && (((b[1] & 0x80) != 0) || (b[1] & 0xFF) > 0x1F))
            return new FileSignature("der","application/x-x509-ca-cert"); // broad ASN.1/DER heuristic

        // ---- Databases / misc ----
        if (startsWithAscii(b, "SQLite format 3\u0000")) return new FileSignature("sqlite","application/x-sqlite3");

        // ---- Fallbacks ----
        if (looksLikeText(b)) return new FileSignature("txt","text/plain");
        return BIN;
    }

    /** Lenient hex → InputStream for Mendix Core.storeFileDocumentContent */
    public static InputStream hexToInputStream(String hex) throws DecoderException {
        byte[] data = decodeLenientHex(hex);
        return new ByteArrayInputStream(data);
    }

    /** Detect extension+MIME directly from hex. */
    public static FileSignature detectFromHex(String hex) throws Exception {
        return detect(decodeLenientHex(hex));
    }

    // ----------------- Helpers -----------------

    public static byte[] decodeLenientHex(String input) throws DecoderException {
        if (input == null) throw new IllegalArgumentException("input is null");
        String s = input.trim();
        if (s.startsWith("0x") || s.startsWith("0X")) s = s.substring(2);
        s = s.replaceAll("[\\s:_-]", "");
        if ((s.length() & 1) != 0) throw new IllegalArgumentException("Invalid hex: odd length");
        if (!s.matches("^[0-9A-Fa-f]+$")) throw new IllegalArgumentException("Invalid hex: non-hex character");
        return Hex.decodeHex(s);
    }

    private static boolean startsWith(byte[] b, int... sig) {
        if (b.length < sig.length) return false;
        for (int i = 0; i < sig.length; i++) {
            if ((b[i] & 0xFF) != sig[i]) return false;
        }
        return true;
    }

    private static boolean startsWithAscii(byte[] b, String ascii) {
        byte[] a = ascii.getBytes(StandardCharsets.US_ASCII);
        if (b.length < a.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (b[i] != a[i]) return false;
        }
        return true;
    }

    private static boolean bytesAtAscii(byte[] b, int offset, String ascii) {
        byte[] a = ascii.getBytes(StandardCharsets.US_ASCII);
        if (offset < 0 || b.length < offset + a.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (b[offset + i] != a[i]) return false;
        }
        return true;
    }

    private static boolean anyNameStartsWith(Set<String> names, String prefix) {
        for (String n : names) if (n.startsWith(prefix)) return true;
        return false;
    }

    private static Set<String> listZipEntries(byte[] b, int limit) throws IOException {
        Set<String> names = new HashSet<>();
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(b))) {
            ZipEntry e;
            int count = 0;
            while ((e = zis.getNextEntry()) != null && count < limit) {
                names.add(e.getName());
                count++;
            }
        }
        return names;
    }

    private static boolean looksLikeXml(byte[] b) {
        int i = skipBomAndWs(b);
        return i >= 0 && i + 1 < b.length && (b[i] == '<');
    }

    private static boolean looksLikeJson(byte[] b) {
        int i = skipBomAndWs(b);
        if (i < 0) return false;
        byte c = b[i];
        return c == '{' || c == '[' || c == '"' || c == 'n' || c == 't' || c == 'f' || c == '-'
                || (c >= '0' && c <= '9');
    }

    private static boolean looksLikeText(byte[] b) {
        int max = Math.min(b.length, 512);
        for (int i = 0; i < max; i++) {
            int v = b[i] & 0xFF;
            if (v == 9 || v == 10 || v == 13) continue;      // tab, LF, CR
            if (v < 32 || v == 127) return false;            // non-printable
        }
        return true;
    }

    private static int skipBomAndWs(byte[] b) {
        int i = 0;
        if (b.length >= 3 && (b[0] & 0xFF) == 0xEF && (b[1] & 0xFF) == 0xBB && (b[2] & 0xFF) == 0xBF) i = 3; // UTF-8 BOM
        while (i < b.length) {
            byte c = b[i];
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n') { i++; continue; }
            break;
        }
        return i >= b.length ? -1 : i;
    }
}