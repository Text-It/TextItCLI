package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommentPage {

    private static final DataBase db = new DataBase();
    private static final DataBase.Comment commentdb = db.new Comment();

    public static void main(String[] args) throws SQLException {
        int userID = Integer.parseInt(args[0]);
        int postID = Integer.parseInt(args[1]);

        Scanner sc = new Scanner(System.in);
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

        int pageSize = 15;     // load 15 at a time
        int offset = 0;        // starting position in DB
        List<String[]> buffer = new ArrayList<>(); // local cache
        int bufferIndex = 0;   // current index inside buffer

        // First fetch
        buffer = commentdb.getComments(postID, pageSize, offset);

        while (true) {

            CommonMethods.clearConsole();


            System.out.println("-".repeat(boxLength));
            System.out.println(" ".repeat((boxLength - 8) / 2) + "COMMENTS" + " ".repeat((boxLength - 8) / 2));
            System.out.println("-".repeat(boxLength));

            if (buffer.isEmpty()) {
                if (offset == 0) {
                    // no comments at all
                    System.out.println(border + " ".repeat((spaceLeftForContent - 14) / 2) + "No Comments Yet" +
                            " ".repeat((spaceLeftForContent - 14) / 2) + border);
                } else {
                    // reached the end
                    System.out.println(border + " ".repeat((spaceLeftForContent - 17) / 2) + "No More Comments" +
                            " ".repeat((spaceLeftForContent - 17) / 2) + border);
                }
            } else {
                // Show current comment
                String[] comment = buffer.get(bufferIndex);
                String user = comment[0];
                String text = comment[1];
                String time = comment[2];

                // plain text time (no emoji)
                System.out.println(border + " @" + user + "  " + time +
                        " ".repeat(Math.max(0, spaceLeftForContent - (user.length() + time.length() + 3))) + border);

                CommonMethods.paragraphDisplay(text, border, spaceLeftForContent);
                System.out.println(border + "-".repeat(spaceLeftForContent) + border);
            }

            // Options
            System.out.println(border + "Options:" + " ".repeat(spaceLeftForContent - 8) + border);
            System.out.println(border + "1) Add Comment" + " ".repeat(spaceLeftForContent - 14) + border);
            System.out.println(border + "2) Previous Comment " + " ".repeat(spaceLeftForContent - 20) + border);
            System.out.println(border + "3) Next Comment " + " ".repeat(spaceLeftForContent - 16) + border);
            System.out.println(border + "4) Back" + " ".repeat(spaceLeftForContent - 7) + border);
            System.out.println("-".repeat(boxLength));

            System.out.print("Select an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter your comment: ");
                    String newComment = sc.nextLine();
                    commentdb.addComment(postID, userID, newComment);
                    db.addNotification(userID,db.featchIdByPostId(postID),"comments", CommonMethods.featchIdForNotification(newComment,"comments"));
                    // reset pagination after adding new comment
                    offset = 0;
                    buffer = commentdb.getComments(postID, pageSize, offset);
                    bufferIndex = 0;
                }
                case 2 -> {
                    if (bufferIndex > 0) {
                        bufferIndex--;
                    } else if (offset > 0) {
                        // load previous batch
                        offset -= pageSize;
                        buffer = commentdb.getComments(postID, pageSize, offset);
                        bufferIndex = buffer.size() - 1;
                    } else {
                        System.out.println("Already at the first comment.");
                    }
                }
                case 3 -> {
                    if (bufferIndex < buffer.size() - 1) {
                        bufferIndex++;
                    } else {
                        // reached end of buffer → load next batch
                        offset += pageSize;
                        List<String[]> nextBatch = commentdb.getComments(postID, pageSize, offset);
                        if (!nextBatch.isEmpty()) {
                            buffer = nextBatch;
                            bufferIndex = 0;
                        } else {
                            System.out.println("No more comments.");
                            offset -= pageSize; // rollback offset if nothing found
                        }
                    }
                }
                case 4 -> {
                    return; // Back to Feed
                }
                default -> System.out.println("Invalid option, try again!");
            }
        }
    }
}
