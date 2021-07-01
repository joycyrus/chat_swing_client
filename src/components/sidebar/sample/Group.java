package components.sidebar.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import components.add_group_member.AddGroupMembers;
import models.*;
import socket.IndexSocket;
import utils.CommonUtil;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Group extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel topPanel;
    private JLabel friendsLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Group frame = new Group();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Group() throws JsonProcessingException {
        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setTitle("Group members");
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(230, 230, 250));
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setPreferredSize(new Dimension(270,0));
        contentPane.add(rightPanel, BorderLayout.EAST);
        rightPanel.setLayout(new BorderLayout(0, 0));

        topPanel = new JPanel();
        topPanel.setForeground(new Color(128, 128, 128));
        topPanel.setBorder(new EmptyBorder(10, 30, 15, 30));
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setPreferredSize(new Dimension(0, 65));
        rightPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BorderLayout(0, 0));
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\DELL\\Desktop\\chat_swing_client\\src\\components\\sidebar\\imgs\\search.jpg"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);

        friendsLabel = new JLabel("Group members");
        friendsLabel.setBackground(new Color(240, 240, 240));
        friendsLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        topPanel.add(friendsLabel, BorderLayout.SOUTH);
        JPanel universal = new JPanel();

        universal.setLayout(new BorderLayout(0, 0));
        universal.setBackground(new Color(240, 248, 255));
        universal.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane scrollPane =new JScrollPane(universal);
        List<User> userNames = new ArrayList<User>();
        boolean haveMembers=true;
//        String key= "users/";
//        Request request = new Request(new ProfileRequestData(1),key);
        String key= "groups/members";
        Request request = new Request(new ProfileRequestData(1),key);
        ResponseDataSuccessDecoder response = new IndexSocket().execute(request);
        if(response.isSuccess()){
            User[] users = new UserResponseDataDecoder().returnUsersListDecoded(response.getData());
            CommonUtil.addTabs(10, true);
            if (users.length != 0){
                for (User user : users) {
                    System.out.println(user.getUserID()+". "+user.getFname()+" "+user.getLname());
                    CommonUtil.addTabs(10, false);
                    userNames.add(new User(user.getUserID(),user.getLname()+" "+user.getFname()));
                }
            }else{
                System.out.println("No user found in this group");
                haveMembers=false;
            }
        }else {
            System.out.println("failed to fetch users in the given group");
        }
//        List<String> userNames = new ArrayList<String>();
//        userNames.add("Adeline");
//        userNames.add("Babins");
//        userNames.add("Bugu");
//        userNames.add("Curl");
//        userNames.add("Cyrus");
//        userNames.add("Kalisa");
//        userNames.add("Kami");
//        userNames.add("Peterson");
//        userNames.add("Ruby");
//        userNames.add("Webgub");
        JButton btnNewButton = new JButton("ADD");
        btnNewButton.setBackground(new Color(0, 0, 0));
        btnNewButton.setForeground(new Color(255, 255, 255));

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddGroupMembers();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        People p = new People();
        universal.add(p.allUsers(userNames,btnNewButton),BorderLayout.CENTER);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
    }
}
