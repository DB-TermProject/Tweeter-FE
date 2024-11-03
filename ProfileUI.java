import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;  // Ellipse2D import 추가

public class ProfileUI extends JFrame {
    // 상수 정의
    private static final Font TITLE_FONT = new Font("맑은 고딕", Font.BOLD, 15);  // 기본 굵은 폰트
    private static final Font PROFILE_FONT = new Font("맑은 고딕", Font.BOLD, 11);  // 기본 일반 폰트
    private static final Font POST_FONT = new Font("맑은 고딕", Font.PLAIN, 9);  // 기본 일반 폰트
    
    // 색상 정의
    private Color twitterBlue = new Color(29, 161, 242);
    private Color backgroundColor = Color.WHITE;
    private Color textColor = new Color(15, 20, 25);
    private Color secondaryTextColor = new Color(83, 100, 113);

    // 모바일 화면 크기 상수 정의
    private static final int MOBILE_WIDTH = 420;  // iPhone 13 Pro 기준 너비
    private static final int MOBILE_HEIGHT = 700; // iPhone 13 Pro 기준 높이
    
    public ProfileUI() {
        setTitle("Twitter");
        setSize(MOBILE_WIDTH, MOBILE_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        
        // 컴포넌트 생성
        JPanel headerPanel = createHeader();
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel profilePanel = createProfile();
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 구분선 추가
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(239, 243, 244));
        separator.setBackground(new Color(239, 243, 244));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel tweetsPanel = createTweetsPanel();
        tweetsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 스크롤 패널 설정
        JScrollPane scrollPane = new JScrollPane(tweetsPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(backgroundColor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 컴포넌트 추가
        mainPanel.add(headerPanel);
        mainPanel.add(profilePanel);
        mainPanel.add(separator);  // 구분선 추가
        mainPanel.add(scrollPane);
        
        add(mainPanel);
        setVisible(true);
    }
    
    
    // 원형 프로필 이미지를 위한 내부 클래스
    private class RoundedPanel extends JPanel {
        private int radius;
        
        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(backgroundColor);
        
        // 뒤로가기 버튼
        JButton backButton = new JButton("←");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        
        // 타이틀과 트윗 수 추가
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        JLabel nameLabel = new JLabel("President Trump");
        JLabel tweetCountLabel = new JLabel("11K Tweets");
        tweetCountLabel.setForeground(secondaryTextColor);
        tweetCountLabel.setFont(POST_FONT);
        
        titlePanel.add(nameLabel);
        titlePanel.add(tweetCountLabel);
        
        header.add(backButton, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);
        
        return header;
    }
    
    private JPanel createProfile() {
        JPanel profile = new JPanel();
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));
        profile.setBackground(backgroundColor);
    
        // 배경 이미지와 프로필 이미지를 포함할 패널
        JPanel headerImagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // 배경 이미지 그리기 (대통령 전용 헬기 이미지)
                    BufferedImage backgroundImage = ImageIO.read(new URL("https://washington.org/sites/default/files/the-white-house_00-718dbdd65056a34_718dbf8e-5056-a348-3a9992829d1583f1.jpg"));
                    g.drawImage(backgroundImage, 0, 0, MOBILE_WIDTH, 200, this);
    
                    // 프로필 이미지 그리기
                    BufferedImage profileImage = ImageIO.read(new URL("https://cdn.hankyung.com/photo/202411/ZA.38491202.1.jpg"));
    
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                    // 프로필 이미지 위치와 크기 설정
                    int diameter = 80; // 프로필 이미지 크기
                    int x = 20;        // 왼쪽 여백
                    int y = 100;       // 상단에서의 거리
    
                    // 원형 클리핑 마스크 생성
                    Ellipse2D.Float circle = new Ellipse2D.Float(x, y, diameter, diameter);
                    g2.setClip(circle);
    
                    // 프로필 이미지 그리기
                    g2.drawImage(profileImage, x, y, diameter, diameter, null);
    
                    // 흰색 테두리 추가
                    g2.setClip(null);
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(4));
                    g2.draw(circle);
    
                    g2.dispose();
                } catch (Exception e) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
    
        headerImagePanel.setPreferredSize(new Dimension(MOBILE_WIDTH, 200));
        headerImagePanel.setMaximumSize(new Dimension(MOBILE_WIDTH, 200));
    
        // 프로필 정보 패널
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(backgroundColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(60, 15, 15, 15));

        // Following 버튼
        JButton followingButton = new JButton("Following");
        followingButton.setBackground(new Color(15, 20, 25));  // 더 진한 검은색
        followingButton.setForeground(Color.WHITE);
        followingButton.setBorderPainted(true);
        followingButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        followingButton.setPreferredSize(new Dimension(100, 35));  // 버튼 크기 조정
        followingButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));

        // 프로필 정보 레이블들
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        namePanel.setBackground(backgroundColor);
        
        JLabel nameLabel = new JLabel("President Trump ");
        nameLabel.setFont(TITLE_FONT);  // TITLE_FONT 사용
        
        JLabel verifiedLabel = new JLabel("✓");
        verifiedLabel.setFont(TITLE_FONT);  // TITLE_FONT 사용
        verifiedLabel.setForeground(twitterBlue);
        
        namePanel.add(nameLabel);
        namePanel.add(verifiedLabel);

        // infoPanel의 여백 수정
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 15, 15, 15));

        // 프로필 정보 순서 및 간격 수정
        JLabel handleLabel = new JLabel("@POTUS");
        handleLabel.setFont(POST_FONT);
        handleLabel.setForeground(new Color(83, 100, 113));  // 더 연한 회색

        // US government account 레이블 추가
        JLabel govLabel = new JLabel("🏛️ US government account");
        govLabel.setFont(POST_FONT);
        govLabel.setForeground(secondaryTextColor);

        // 팔로워 정보 수정
        JLabel followingLabel = new JLabel("39 Following  ·  33.4M Followers");  // 중간에 점 추가
        followingLabel.setFont(POST_FONT);  // POST_FONT 사용
        followingLabel.setForeground(textColor);

        JLabel bioLabel = new JLabel("45th President of the United States of America");
        bioLabel.setFont(POST_FONT);  // POST_FONT 사용

        JLabel locationLabel = new JLabel("📍 Washington, D.C.");
        locationLabel.setFont(POST_FONT);  // POST_FONT 사용
        locationLabel.setForeground(secondaryTextColor);

        JLabel websiteLabel = new JLabel("🔗 WhiteHouse.gov");
        websiteLabel.setFont(POST_FONT);  // POST_FONT 사용
        websiteLabel.setForeground(twitterBlue);

        JLabel joinedLabel = new JLabel("📅 Joined January 2017");
        joinedLabel.setFont(POST_FONT);  // POST_FONT 사용
        joinedLabel.setForeground(secondaryTextColor);

        // Following 버튼을 포함할 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(followingButton);

        // 컴포넌트 추가
        infoPanel.add(buttonPanel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(namePanel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(handleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(bioLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(locationLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(websiteLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(joinedLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(followingLabel);

        profile.add(headerImagePanel);
        profile.add(infoPanel);

        return profile;
    }
    
    private JPanel createTweetsPanel() {
        JPanel tweetsPanel = new JPanel();
        tweetsPanel.setLayout(new BoxLayout(tweetsPanel, BoxLayout.Y_AXIS));
        tweetsPanel.setBackground(backgroundColor);
        
        // 트윗 추가
        addTweet(tweetsPanel, 
            "As I have been saying for a long time, Twitter has gone further and further in banning free speech...",
            "1m", 456, 2500, 5600);
        addTweet(tweetsPanel,
            "...also look at the possibilities of building out our own platform in the near future...",
            "1m", 32, 860, 2700);
        addTweet(tweetsPanel,
            "...STAY TUNED!",
            "1m", 116, 670, 2700);
        addTweet(tweetsPanel,
            "As I have been saying for a long time, Twitter has gone further and further in banning free speech...",
            "1m", 116, 670, 2700);
        addTweet(tweetsPanel,
            "...STAY TUNED!",
            "1m", 116, 670, 2700);
        addTweet(tweetsPanel,
            "As I have been saying for a long time, Twitter has gone further and further in banning free speech...",
            "1m", 116, 670, 2700);
        addTweet(tweetsPanel,
            "...STAY TUNED!",
            "1m", 116, 670, 2700);
        addTweet(tweetsPanel,
            "As I have been saying for a long time, Twitter has gone further and further in banning free speech...",
            "1m", 116, 670, 2700);
        // 패널 크기 설정
        tweetsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tweetsPanel.setPreferredSize(new Dimension(MOBILE_WIDTH, tweetsPanel.getPreferredSize().height));
        tweetsPanel.setMaximumSize(new Dimension(MOBILE_WIDTH, Integer.MAX_VALUE));
        tweetsPanel.setFont(POST_FONT);
        return tweetsPanel;
    }
    
    private void addTweet(JPanel container, String content, String time, 
                        int comments, int retweets, int likes) {
        // 트윗 패널 - BorderLayout의 수평 간격을 20으로 설정
        JPanel tweetPanel = new JPanel(new BorderLayout(20, 0));  // 수평 간격 20픽셀
        tweetPanel.setBackground(backgroundColor);
        tweetPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(239, 243, 244)),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        // 프로필 이미지
        try {
            BufferedImage profileImg = ImageIO.read(new URL("https://cdn.hankyung.com/photo/202411/ZA.38491202.1.jpg"));
            Image resizedImage = profileImg.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            
            JPanel roundedImagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    Ellipse2D.Float circle = new Ellipse2D.Float(0, 0, 48, 48);
                    g2.setClip(circle);
                    g2.drawImage(resizedImage, 0, 0, this);
                    g2.dispose();
                }
                
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(48, 48);
                }
            };
            roundedImagePanel.setOpaque(false);
            tweetPanel.add(roundedImagePanel, BorderLayout.WEST);
        } catch (Exception e) {
            e.printStackTrace();
            // 이미지 로드 실패시 기본 원형 패널 표시
            JPanel defaultPanel = new JPanel();
            defaultPanel.setPreferredSize(new Dimension(48, 48));
            defaultPanel.setBackground(Color.LIGHT_GRAY);
            tweetPanel.add(defaultPanel, BorderLayout.WEST);
        }
        
            // 컨텐츠 패널
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(backgroundColor);
        
        // 헤더 (이름, 아이디, 시간)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        headerPanel.setBackground(backgroundColor);
        JLabel nameLabel = new JLabel("President Trump");
        nameLabel.setFont(PROFILE_FONT);
        JLabel usernameLabel = new JLabel("@POTUS · " + time);
        usernameLabel.setForeground(secondaryTextColor);
        usernameLabel.setFont(PROFILE_FONT);
        
        headerPanel.add(nameLabel);
        headerPanel.add(usernameLabel);
        
        // 트윗 내용
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textPanel.setFont(POST_FONT);
        textPanel.setBackground(backgroundColor);
        
        // 트윗 내용의 폰트 크기와 색상 수정
        JLabel tweetContent = new JLabel("<html><p style='width:" + (MOBILE_WIDTH - 100) + "px'>" + content + "</p></html>");
        tweetContent.setFont(new Font("맑은 고딕", Font.PLAIN, 11));  // 폰트 크기 증가
        tweetContent.setForeground(new Color(15, 20, 25));  // 더 진한 텍스트 색상
        
        // 상호작용 버튼
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        interactionPanel.setBackground(backgroundColor);
        
        // 더 정확한 트위터 스타일의 아이콘으로 변경
        addInteractionButton(interactionPanel, "💭", comments);    // 댓글
        addInteractionButton(interactionPanel, "🔁", retweets);    // 리트윗
        addInteractionButton(interactionPanel, "♥️", likes);       // 좋아요
        addInteractionButton(interactionPanel, "📤", null);        // 공유
        
        // 컴포넌트 조립
        contentPanel.add(headerPanel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(textPanel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(interactionPanel);
        
        tweetPanel.add(contentPanel, BorderLayout.CENTER);
        
        // 트윗 패널의 크기 제한 설정
        tweetPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, tweetPanel.getPreferredSize().height));
        
        container.add(tweetPanel);
    }
    
    
    private void addInteractionButton(JPanel container, String icon, Integer count) {
        JButton button = new JButton(icon + (count != null ? " " + count : ""));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(secondaryTextColor);
        button.setFont(POST_FONT);
        
        // 호버 효과
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(twitterBlue);
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(secondaryTextColor);
            }
        });
        
        container.add(button);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new ProfileUI());  // TwitterUI를 ProfileUI로 변경
    }
}