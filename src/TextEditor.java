import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
public class TextEditor implements ActionListener{
    JMenuItem New,open,saveas,save,delete,exit,cut,copy,clear,paste,select,wordwrap,zoom_in,zoom_out,normal,find,replace,darktheme,lighttheme,font,font_color,background_color,about;
    JTextArea textarea;
    JCheckBoxMenuItem status;
    JFrame jf,font_frame;
    File file;
    @SuppressWarnings("rawtypes")
    JComboBox font_family,font_size,font_style;
    JButton ok;
    private String text = "",textToSearch = "";
    private static LinkedStack stack = new LinkedStack();
    
    @SuppressWarnings("deprecation")
    TextEditor()
    {
        jf=new JFrame("Untitled-Notepad");
        jf.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 8, 10, 8, Color.lightGray));
        Image icon = Toolkit.getDefaultToolkit().getImage("Resources\\notepad.png");   
        jf.setIconImage(icon);
        jf.setSize(700,500);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textarea = new JTextArea();
        textarea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if((int)e.getKeyChar()==8) { 
					if(!stack.isEmpty()) {
						stack.pop();	
					}
				}
				else {
					stack.push(e.getKeyChar());
				}
			}
		});
        jf.add(textarea);
       
        JMenuBar jmenubar = new JMenuBar();
        jmenubar.setBackground(Color.black);
    
        JMenu file = new JMenu("File");
        file.setMnemonic( KeyEvent.VK_F);
        jmenubar.add(file);
        file.setForeground(Color.WHITE);
        file.add(new JSeparator());
       
        New = new JMenuItem("New");
        New.setMnemonic(KeyEvent.VK_N);
        New.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK, true));
        New.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		NewFile();
        	}
        });
        file.add(New);
        New.setIcon(new ImageIcon("Resources\\neww.png"));
        
        open= new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK, true));
        open.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		OpenFile();
        	}
        });
        file.add(open);
        open.add(new JSeparator());
        open.setIcon(new ImageIcon("Resources\\open.png"));
        
        save=new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK, true));
        save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SaveFile();
        	}
        });
        file.add(save);
        save.add(new JSeparator());
        save.setIcon(new ImageIcon("Resources\\save.png"));

        saveas = new JMenuItem("Save AS");
        saveas.setMnemonic(KeyEvent.VK_V);
        saveas.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK, true));
        saveas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SaveAsFile();
        	}
        });
        file.add(saveas);
        saveas.add(new JSeparator());
        saveas.setIcon(new ImageIcon("Resources\\save.png"));

        exit= new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK, true));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
        file.add(exit);
        exit.add(new JSeparator());
        exit.setIcon(new ImageIcon("Resources\\exit.png"));

        
        JMenu edit= new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);
        jmenubar.add(edit);
        edit.setForeground(Color.white);
     
        cut=new JMenuItem("Cut");
        cut.setMnemonic(KeyEvent.VK_X);
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK, true));
        cut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textarea.cut();
        	}
        });
        edit.add(cut);
        cut.setIcon(new ImageIcon("Resources\\cut.png"));
        
        copy=new JMenuItem("Copy");
        copy.setMnemonic(KeyEvent.VK_C);
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK, true));
        copy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textarea.copy();
        	}
        });
        edit.add(copy);
        copy.add(new JSeparator());
        copy.setIcon(new ImageIcon("Resources\\copy.png"));

        delete = new JMenuItem("Delete");
        delete.setMnemonic(KeyEvent.VK_D);
        delete.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK, true));
        delete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		text = textarea.getSelectedText();
        		textarea.replaceSelection("");
        	}
        });
        edit.add(delete);
        delete.add(new JSeparator());
        delete.setIcon(new ImageIcon("Resources\\delete.png"));

        
        paste=new JMenuItem("Paste");
        paste.setMnemonic(KeyEvent.VK_V);
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK, true));
        paste.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textarea.paste();
        	}
        });
        edit.add(paste);
        paste.add(new JSeparator());
        paste.setIcon(new ImageIcon("Resources\\paste.png"));
        
        clear=new JMenuItem("Clear");
        clear.setMnemonic(KeyEvent.VK_S);
        clear.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK, true));
        clear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  int cask = JOptionPane.showConfirmDialog(null,"Are you sure you want to clear the document?");
         			if (cask == JOptionPane.YES_OPTION) {
         				textarea.setText("");
                }
        	}
        });
        edit.add(clear);
        clear.add(new JSeparator());
        clear.setIcon(new ImageIcon("Resources\\clear.png"));
        
        select = new JMenuItem("Select All");
        select.setMnemonic(KeyEvent.VK_L);
        select.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK, true));
        select.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textarea.selectAll();
        }});
        edit.add(select);
        select.add(new JSeparator());
        select.setIcon(new ImageIcon("Resources\\select.png"));
        
        wordwrap= new JMenuItem("Word Wrap");
        wordwrap.setMnemonic(KeyEvent.VK_W);
        wordwrap.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_MASK, true));
        wordwrap.addActionListener(new  ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textarea.setLineWrap(true);
          	  textarea.setWrapStyleWord(true);
        	}
        });
        edit.add(wordwrap);
        wordwrap.add(new JSeparator());
        wordwrap.setSelected(true);
        wordwrap.setIcon(new ImageIcon("Resources\\wordwrap.png"));

        JMenu format = new JMenu("Format");
        format.setMnemonic(KeyEvent.VK_M);
        jmenubar.add(format);
        format.setForeground(Color.white);
     
        font = new JMenuItem("Font");
        font.setMnemonic(KeyEvent.VK_F);
        font.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK, true));
        font.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		OpenFontFrame();
        	}
        });
        format.add(font);
        font.setIcon(new ImageIcon("Resources\\font.png"));
        
        font_color = new JMenuItem("Color");
        font_color.setMnemonic(KeyEvent.VK_C);
        font_color.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK, true));
        font_color.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  Color c=JColorChooser.showDialog(jf, "Select Color For Font", Color.white);
                  textarea.setForeground(c);
        	}
        });
        format.add(font_color);
        font_color.add(new JSeparator());
        font_color.setIcon(new ImageIcon("Resources\\font_color.png"));
        
        background_color = new JMenuItem("Background");
        background_color.setMnemonic(KeyEvent.VK_B);
        background_color.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK, true));
        background_color.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  Color c=JColorChooser.showDialog(jf, "Select Color For Background", Color.white);
                  textarea.setBackground(c);
        	}
        });
        format.add(background_color);
        background_color.add(new JSeparator());
        background_color.setIcon(new ImageIcon("Resources\\background_color.png"));
        
        
        JMenu search= new JMenu("Search");
        search.setMnemonic(KeyEvent.VK_S);
       jmenubar.add(search);
        search.setForeground(Color.white);
        
        find= new JMenuItem("Find");
        find.setMnemonic(KeyEvent.VK_F);
        find.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK, true));
        find.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	 textToSearch = JOptionPane.showInputDialog("Enter text you want to search");
                 if(textToSearch != null)
                 {
                     text=textarea.getText();
                     int searchIndex = text.indexOf(textToSearch);
                     if(searchIndex == -1)
                         JOptionPane.showMessageDialog(null,"No Match Result Found","Find",JOptionPane.ERROR);
                     textarea.select(searchIndex, searchIndex+textToSearch.length());
                 }
             }
        });
        search.add(find);
        find.setIcon(new ImageIcon("Resources\\find.png"));
        
        replace = new JMenuItem("Replace");
        replace.setMnemonic(KeyEvent.VK_R);
        replace.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK, true));
        replace.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String find=JOptionPane.showInputDialog(this, "Enter the word to search for:");
              	String replace=JOptionPane.showInputDialog(this, "Enter the word for replacement:");
              	textarea.setText(textarea.getText().replaceAll(find, replace));
        	}
        });
        search.add(replace);
        replace.setIcon(new ImageIcon("Resources\\replace.png"));
        replace.add(new JSeparator());
       
        JMenu theme= new JMenu("Theme");
        theme.setMnemonic(KeyEvent.VK_T);
        jmenubar.add(theme);
        theme.setForeground(Color.white);
        
        lighttheme= new JMenuItem("Light Theme");
        lighttheme.setMnemonic(KeyEvent.VK_L);
        lighttheme.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK, true));
        lighttheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	  textarea.setBackground(Color.white);
        		  textarea.setForeground(Color.BLACK);
            }
        });
        theme.add(lighttheme);
        lighttheme.setIcon(new ImageIcon("Resources\\theme.png"));
        
        darktheme= new JMenuItem("Dark Theme");
        darktheme.setMnemonic(KeyEvent.VK_D);
        darktheme.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK, true));
        darktheme.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		  textarea.setBackground(Color.black);
        		  textarea.setForeground(Color.white);
        	}
        });
        theme.add(darktheme);
        darktheme.setIcon(new ImageIcon("Resources\\theme.png"));
        darktheme.add(new JSeparator());
      
        JMenu Help= new JMenu("Help");
        Help.setMnemonic(KeyEvent.VK_H);
        jmenubar.add(Help);
        Help.setForeground(Color.white);
       
        about= new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK, true));
        about.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "Created By: Duaa Zehra\nFor Making Notes Or Documenting Something.", "Notepad", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        Help.add(about);
        about.setIcon(new ImageIcon("Resources\\about.png")); 
       
        jf.setJMenuBar(jmenubar);
        jf.setVisible(true);
        
        JScrollPane scrollpane = new JScrollPane(textarea);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        jf.add(scrollpane);
    }

    public static void main(String[] args) {
         new TextEditor();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
    public void SetFontOnTextArea()
    {
         String fontfamily=(String)font_family.getSelectedItem();
              String fontsize=(String)font_size.getSelectedItem();
              String fontstyle=(String)font_style.getSelectedItem();
              
              int style=0;
              if(fontstyle.equals("Plain"))
              {
                  style=0;
              }
              else if(fontstyle.equals("Bold"))
              {
                  style=1;
              }
              else if(fontstyle.equals("Italic"))
              {
                  style=2;
              }
              Font fontt= new Font(fontfamily,style,Integer.parseInt(fontsize));
              textarea.setFont(fontt);
              font_frame.setVisible(false);
    }
        
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void OpenFontFrame()
    {

        
        font_frame= new JFrame("Select_font");
        font_frame.setSize(400, 400);
        font_frame.setVisible(true);
        font_frame.setLocationRelativeTo(jf);
        font_frame.setLayout(null);
        
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font_family= new JComboBox(fonts);
        font_family.setBounds(30, 100, 100, 30);
        font_frame.add(font_family);
        
        String[] sizes={"12","14","16","18","20","22","24","26","30","72"};
        font_size= new JComboBox(sizes);
        font_size.setBounds(140, 100, 100, 30);
        font_frame.add(font_size);
        
        String[] styles={"Plain","Bold","Italic"};
        font_style= new JComboBox(styles);
        font_style.setBounds(250, 100, 100, 30);
        font_frame.add(font_style);
        
        ok=new JButton("Save Changes");
        ok.setBounds(90, 200, 200, 50);
        ok.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SetFontOnTextArea();
        	}
        });
        ok.setBackground(Color.black);
        ok.setForeground(Color.white);
        font_frame.add(ok);
    }
    public void OpenFile()
    {
        JFileChooser fileChooser = new JFileChooser();
           int result = fileChooser.showOpenDialog(jf);
           file=fileChooser.getSelectedFile();
           
           if(result==0)
           {
               textarea.setText(" ");
                try
                {
                         @SuppressWarnings("resource")
                        FileInputStream fis = new FileInputStream(file);
                         jf.setTitle(file.getName());
                         int i;
                         while((i=fis.read())  !=-1)
                         {
                             textarea.append(String.valueOf((char)i));
                         }
            
                 }
             catch(IOException e)
                 {
                     System.out.println(e);
                 }
           }
    }
    public void SaveFile()
    {
        String title=jf.getTitle();
        if(title.equals("Untitled-Notepad"))
        {
            SaveAsFile();
        }
        else
        {
                String text=textarea.getText();
                try(FileOutputStream fos = new FileOutputStream(file))
                    {  
                       byte[] b=text.getBytes();
                       fos.write(b);
                    }
                catch(IOException e)
                    {
                       e.printStackTrace();
                    }
        }
    }
    public void SaveAsFile()
    {
        JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(jf);
            file=fileChooser.getSelectedFile();
            jf.setTitle(file.getName());
            if(result==0)
            {
                String text=textarea.getText();
                try(FileOutputStream fos = new FileOutputStream(file))
                    {  
                       byte[] b=text.getBytes();
                       fos.write(b);
                    }
                catch(IOException ee)
                    {
                       ee.printStackTrace();
                    }
            }
    }
    public void NewFile()
    {
         String text=textarea.getText();
            if(!text.equals(""))
            {
                int i=JOptionPane.showConfirmDialog(jf, "Do you want to save file");
                if(i==0)
                {
                    SaveAsFile();
                    textarea.setText("");
                    jf.setTitle("Untitled-Notepad");
                }
                else 
                {
                    textarea.setText("");
                    jf.setTitle("Untitled-Notepad");
                }
            }
    }
   }
