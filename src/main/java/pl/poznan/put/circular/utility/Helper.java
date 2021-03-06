package pl.poznan.put.circular.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.svg.SVGDocument;
import pl.poznan.put.circular.Angle;
import pl.poznan.put.circular.Axis;
import pl.poznan.put.circular.Circular;
import pl.poznan.put.circular.enums.ValueType;
import pl.poznan.put.utility.svg.Format;
import pl.poznan.put.utility.svg.SVGHelper;

/** A class providing helper functions. */
public final class Helper {
  /**
   * Read a resource of this project.
   *
   * @param name Name of the resource.
   * @return Resource as a {@link String}
   * @throws IOException If the I/O on resource reading fails.
   */
  public static String readResource(final String name) throws IOException {
    final ClassLoader classLoader = Helper.class.getClassLoader();
    try (final InputStream stream = classLoader.getResourceAsStream(name)) {
      return IOUtils.toString(stream, Charset.defaultCharset());
    }
  }

  /**
   * Export given {@link SVGDocument} into a {@link File}.
   *
   * @param svg Input SVG image.
   * @param file Output file.
   * @throws IOException When writing to file fails.
   */
  public static void exportSvg(final SVGDocument svg, final File file) throws IOException {
    try (final OutputStream stream = new FileOutputStream(file)) {
      IOUtils.write(SVGHelper.export(svg, Format.SVG), stream);
    }
  }

  /**
   * Parse input string in the following way: lines beginning with # are ignored, all other are
   * tokenized. Each token is treated as HH.MM datapoint converted into an {@link Circular}.
   *
   * @param content Input string.
   * @return A list of {@link Circular} values as parsed from the input.
   */
  public static List<Angle> loadHourMinuteData(final String content) {
    final String[] lines = StringUtils.split(content, '\n');
    final List<Angle> data = new ArrayList<>(lines.length);

    for (final String line : lines) {
      if (!line.isEmpty() && (line.charAt(0) == '#')) {
        continue;
      }

      for (final String token : StringUtils.split(line)) {
        if (!StringUtils.isBlank(token)) {
          data.add(Angle.fromHourMinuteString(token));
        }
      }
    }

    return data;
  }

  /**
   * Parse input string in the following way: lines beginning with # are ignored, all other are
   * tokenized. Each token is a value in degrees which is converted into an {@link Axis}.
   *
   * @param content Input string.
   * @return A list of {@link Axis} values as parsed from the input.
   */
  public static List<Axis> loadAxisData(final String content) {
    final String[] lines = StringUtils.split(content, '\n');
    final List<Axis> data = new ArrayList<>(lines.length);

    for (final String line : lines) {
      if (!line.isEmpty() && (line.charAt(0) == '#')) {
        continue;
      }

      for (final String token : StringUtils.split(line)) {
        if (!StringUtils.isBlank(token)) {
          final double degrees = Double.parseDouble(token);
          data.add(new Axis(degrees, ValueType.DEGREES));
        }
      }
    }

    return data;
  }

  private Helper() {
    super();
  }
}
