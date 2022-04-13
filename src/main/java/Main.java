import model.BusCompany;
import util.Util;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static util.Util.COMPANY_GROTTY;
import static util.Util.COMPANY_POSH;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return;
        }

        String pathToInputFile = console.readLine("Enter your path to file txt: ");
        System.out.println("Your output file will be created in the same place.");

        try (Stream<String> lines = Util.readFile(pathToInputFile)) {

            List<BusCompany> busCompanies = Util.parseToObjects(lines);

            final Set<BusCompany> result = new HashSet<>();
            final int maxIndex = busCompanies.size() - 1;

            for (int i = 0; i <= maxIndex; i++) {
                final BusCompany thisBusCompany = busCompanies.get(i);

                boolean best = true;
                for (int j = 0; j <= maxIndex; j++) {
                    final BusCompany thatBusCompany = busCompanies.get(j);

                    if (thisBusCompany.compareTo(thatBusCompany) < 0) {
                        best = false;
                        break;
                    }
                }
                if (best) {
                    result.add(thisBusCompany);
                }
            }

            busCompanies = new ArrayList<>(result);

            List<BusCompany> busCompanyPosh =
                    Util.getBusCompaniesByName(busCompanies, COMPANY_POSH);

            List<BusCompany> busCompanyGrotty =
                    Util.getBusCompaniesByName(busCompanies, COMPANY_GROTTY);

            String output = Arrays.toString(busCompanyPosh.toArray()) + "\n\n"
                    + Arrays.toString(busCompanyGrotty.toArray());

            //TODO
            output = output
                    .replaceAll("]", "")
                    .replaceAll("\\[", "")
                    .replaceAll(", ", "\n")
                    .trim();

            Path pathToOutputFile = Path.of(Util.getPathToOutput(pathToInputFile) + "/output.txt");

            Files.writeString(pathToOutputFile, output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
