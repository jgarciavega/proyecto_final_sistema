
package proyecto_final_sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Ventana extends JFrame {
    //Declarando los paneles
    JPanel splash, login, signin, cuenta, bloquear, listausuarios;
    String panel_activo = "";
    //Fuentes de texto que usaremos
    Font header = new Font(Font.SERIF, Font.PLAIN, 40);
    Font normal_label = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
    //Variable para saber que si estamos logeados
    Boolean loggeado = false;
    //Aqui se almacenara nuestra informacion
    String[] info = {};
    String[][] data;
    Image icono = Toolkit.getDefaultToolkit().getImage("logo.png");

    public Ventana() {
        //Declarando opciones para la ventana
        this.setTitle("Sistema");
        this.setSize(400, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setIconImage(icono);
        this.show_splash();
        //Declarando La barra de menu
        JMenuBar menu = new JMenuBar();
        JMenu cuenta = new JMenu("Cuenta");
        JMenu usuarios = new JMenu("Usuarios");
        JMenu ayuda = new JMenu("Ayuda");
        menu.add(cuenta);
        menu.add(usuarios);
        menu.add(ayuda);
        JMenuItem micuenta = new JMenuItem("Mi Cuenta");
        JMenuItem bloquear = new JMenuItem("Bloquear");
        JMenuItem cerrarsesion = new JMenuItem("Cerrar Sesion");
        cuenta.add(micuenta);
        cuenta.add(bloquear);
        cuenta.add(cerrarsesion);
        JMenuItem listaUsuarios = new JMenuItem("Lista Usuarios");
        JMenuItem crearUsuario = new JMenuItem("Crear Usuario");
        usuarios.add(listaUsuarios);
        usuarios.add(crearUsuario);
        JMenuItem ayudaCrearUsuario = new JMenuItem("Como Crear un usuario?");
        JMenuItem ayudaBloquearCuenta = new JMenuItem("Como Bloqueo mi cuenta?");
        ayuda.add(ayudaCrearUsuario);
        ayuda.add(ayudaBloquearCuenta);
        this.setJMenuBar(menu);
        this.invalidate();
        this.revalidate();
        this.repaint();
        ayudaBloquearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "1. Hacer click en la opcion cuenta en la parte superior\n"
                                                  + "2. Hacer click en la opcion Bloquear Cuenta en el menu despegable\n"
                                                  + "3. Introducir contrasena y usuario\n"
                                                  + "4 Hacer click en bloquear\n"
                                                  + "5 Listo su cuenta a sido bloqueada\n", "Como Crear Usuarios?", JOptionPane.DEFAULT_OPTION);
                }
            });
	
        ayudaCrearUsuario.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "1. Hacer click en la opcion Usuarios en el menu superior\n"
                                                  + "2. Hacer click en la opcion Crear Usuario en el menu desplegado\n"
                                                  + "3. Llenar los campos solicitados\n"
                                                  + "4 Escribir una pequena biografia\n"
                                                  + "5 Y por ultimo hacer click en el boton registrar\n", "Como bloquear cuenta?", JOptionPane.DEFAULT_OPTION);
                }
            });
	
        //Listener para el boton lista de usuarios
        listaUsuarios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(info[4].equals("true/") && loggeado) {
                        Ventana.this.cambiarPanel("listausuarios");
                    } else JOptionPane.showMessageDialog(null, "Nesecita ser administrador");
                }
            });

        cerrarsesion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Si cerramos sesion
                    if (loggeado) {
                        //Limpiar informacion de logg
                        Arrays.fill(info, "");
                        // desactivar bandera
                        loggeado = false;
                        cambiarPanel("login");
                    } else JOptionPane.showMessageDialog(null, "Debe de iniciar sesion primero");
                }
            });

        bloquear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (loggeado) {
                        Ventana.this.cambiarPanel("bloquear");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nesecitas estar loggeado para hacer esto");
                    }
                }
            });
        micuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (loggeado) {
                        Ventana.this.cambiarPanel("cuenta");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nesecitas estar loggeado");
                        Ventana.this.cambiarPanel("login");
                    }
                }
            });
        crearUsuario.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cambiarPanel("signin");
                }
            });
    }
    
    //Panel de registro
    public void show_signin() {
        panel_activo = "signin";
        signin = new JPanel();
        signin.setBounds(0, 0, 400, 700);
        signin.setBackground(new Color(0x0B6390));
        signin.setLayout(null);

        JLabel text = new JLabel(("Registrar Usuario"));
        text.setBounds(0, 0, 400, 50);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setForeground(new Color(0xffffff));
        text.setFont(header);

        JCheckBox super_user = new JCheckBox("Admin");
        super_user.setBounds(290, 555, 100, 50);
        super_user.setBackground(new Color(0x0B6390));
        super_user.setForeground(new Color(0xffffff));

        JLabel name_label = new JLabel("Nombre");
        name_label.setFont(normal_label);
        name_label.setBounds(0, 80, 400, 25);
        name_label.setForeground(new Color(0xffffff));
        name_label.setHorizontalAlignment(JLabel.CENTER);

        JTextField name_field = new JTextField();
        name_field.setBounds(0, 120, 400, 30);

        JLabel user_label = new JLabel("Usuario");
        user_label.setFont(normal_label);
        user_label.setBounds(0, 180, 400, 25);
        user_label.setForeground(new Color(0xffffff));
        user_label.setHorizontalAlignment(JLabel.CENTER);

        JTextField user_field = new JTextField();
        user_field.setBounds(0, 220, 400, 30);

        JLabel pass_label = new JLabel("Contrasena");
        pass_label.setFont(normal_label);
        pass_label.setBounds(0, 260, 400, 25);
        pass_label.setHorizontalAlignment(JLabel.CENTER);
        pass_label.setForeground(new Color(0xffffff));

        JPasswordField pass_field = new JPasswordField();
        pass_field.setBounds(0, 300, 400, 30);

        JLabel bio_label = new JLabel("Biografia");
        bio_label.setFont(normal_label);	
        bio_label.setForeground(new Color(0xffffff));
        bio_label.setBounds(0, 340, 400, 30);
        bio_label.setHorizontalAlignment(JLabel.CENTER);

        JTextArea bio = new JTextArea();
        bio.setBackground(new Color(0xF5E4D1));
        bio.setBounds(10, 390, 360, 150);

        JButton enviar = new JButton("Registrarse");
        enviar.setBounds(10, 560, 160, 40);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(180, 560, 100, 40);

        enviar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        //Obtenemos el texto de los campos
                        String name = name_field.getText();
                        String user = user_field.getText();
                        String pass = pass_field.getText();
                        String biogra = bio.getText();
                        boolean superuser = super_user.isSelected();
                        //Hacemos una nueva linea a partir de los campos
                        String text = name + "/" + user + "/" + pass + "/" + biogra + "/" + Boolean.toString(superuser) + "/";
                        FileWriter fr = new FileWriter("users.txt", true);
                        //La escribimos al archivo de texto
                        fr.write(text + "\n");
                        fr.flush();
                        fr.close();
                        JOptionPane.showMessageDialog(null, "Registro exitoso");
                        Ventana.this.cambiarPanel("login");
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
            });
        cancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "Desea salir de verdad?");
                    if (i == 0) System.exit(0);
                }
            });

        signin.add(text);
        signin.add(name_label);
        signin.add(name_field);
        signin.add(user_label);
        signin.add(user_field);
        signin.add(pass_label);
        signin.add(pass_field);
        signin.add(bio_label);
        signin.add(bio);
        signin.add(enviar);
        signin.add(cancelar);
        signin.add(super_user);
        this.add(signin);
    }

    public void show_login() {
        panel_activo = "login";
        login = new JPanel();
        login.setBackground(new Color(0x0B6390));
        login.setBounds(0, 0, 400, 700);
        login.setLayout(null);

        JLabel text = new JLabel("Accede a ");
        text.setFont(new Font("Serif", Font.PLAIN, 40));
        text.setBounds(0, 100, 400, 30);
        text.setForeground(new Color(0xffffff));
        text.setHorizontalAlignment(JLabel.CENTER);

        JLabel text2 = new JLabel("Tu Cuenta");
        text2.setFont(new Font("Serif", Font.PLAIN, 40));
        text2.setBounds(0, 140, 400, 30);
        text2.setForeground(new Color(0xffffff));
        text2.setHorizontalAlignment(JLabel.CENTER);

        JLabel user = new JLabel("Usuario");
        user.setBounds(0, 200, 400, 30);
        user.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        user.setForeground(new Color(0xffffff));
        user.setHorizontalAlignment(JLabel.CENTER);

        JTextField username_field = new JTextField();
        username_field.setBounds(0, 250, 400, 50);

        JLabel pass = new JLabel("Contrasena");
        pass.setBounds(0, 320, 400, 30);
        pass.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
	      pass.setForeground(new Color(0xffffff));
        pass.setHorizontalAlignment(JLabel.CENTER);

        JPasswordField pass_field = new JPasswordField();
        pass_field.setBounds(0, 370, 400, 50);

        JButton iniciar_sesion = new JButton("Iniciar Sesion");
        iniciar_sesion.setBounds(10, 500, 370, 40);
	      iniciar_sesion.setBackground(new Color(0x9CC744));
        iniciar_sesion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(10, 550, 370, 40);
        cancelar.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

        iniciar_sesion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean bloqueado = false;
                    try {
                        //Obtenemosd el usuario y la contrasena
                        String user = username_field.getText();
                        String pass = pass_field.getText();
                        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            //Aqui usamos la funcion split para separar el string en 5 espacios
                            info = line.split("/", 5);
                            BufferedReader blockedBr = new BufferedReader(new FileReader("usuarios_bloqueados.txt"));
                            String lineBloquedo;
                            //Si el usuario y la contrasena son las eque estan en los campos de texto
                            if (info[1].equals(user) && info[2].equals(pass)){
                                //checkamos si esa linea esta en los usuarios bloqueados
                                while((lineBloquedo = blockedBr.readLine()) != null) {
                                    if(lineBloquedo.equals(line)) {
                                        bloqueado = true;
                                        break;
                                    }
                                }
                                if(!bloqueado) {
                                    loggeado = true;
                                } else loggeado = false;
                                cambiarPanel("splash");
                                break;
                            }
                            blockedBr.close();
                        }
                        br.close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                    if (loggeado && !bloqueado) {
                        JOptionPane.showMessageDialog(null, "Credenciales Correctas");
                        Ventana.this.cambiarPanel("cuenta");
                    } else JOptionPane.showMessageDialog(null, "Credenciales incorrectas o usuario bloqueado");
                }
            });

        cancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "Desea salir de verdad?");
                    if (i == 0) System.exit(0);
                }
            });

        login.add(user);
        login.add(username_field);
        login.add(pass);
        login.add(pass_field);
        login.add(text);
        login.add(text2);
        login.add(iniciar_sesion);
        login.add(cancelar);
        this.add(login);
    }

    public void show_splash() {
        panel_activo = "splash";
        splash = new JPanel();
        splash.setLayout(null);
        splash.setBackground(new Color(0xffffff));
        splash.setBounds(0, 0, 400, 700);
        JLabel logo = new JLabel(new ImageIcon("splash.jpg"));
        logo.setFont(new Font("Serif", Font.PLAIN, 40));
        logo.setBounds(0, 0, 400, 700);
        logo.setVerticalAlignment(JLabel.CENTER);
        logo.setHorizontalAlignment(JLabel.CENTER);
        
        splash.add(logo);
        this.add(splash);
        this.invalidate();
        this.revalidate();
        this.repaint();
        try {
            //Mostrar la ventana de splash y esperar 2000 ms
            Thread.sleep(3000);
            cambiarPanel("login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show_cuenta() {
        panel_activo = "cuenta";
        cuenta = new JPanel();
        cuenta.setBounds(0, 0, 400, 700);
        cuenta.setBackground(new Color(0x0C6492));
        cuenta.setLayout(null);

        JLabel text = new JLabel(("Mi Cuenta"));
        text.setBounds(0, 20, 400, 30);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setForeground(new Color(0xffffff));
        text.setFont(header);

        JCheckBox super_user = new JCheckBox("Administrador");
        super_user.setBounds(0, 70, 120, 50);
        super_user.setForeground(new Color(0xffffff));
        super_user.setBackground(new Color(0x0C6492));
        if (info[4].equals("true/")) {
            super_user.setSelected(true);
        }

        JLabel name_label = new JLabel("Nombre");
        name_label.setFont(normal_label);
        name_label.setBounds(0, 80, 400, 20);
        name_label.setForeground(new Color(0xffffff));
        name_label.setHorizontalAlignment(JLabel.CENTER);

        JTextField name_field = new JTextField();
        name_field.setBounds(0, 120, 400, 30);
        name_field.setText(info[0]);

        JLabel user_label = new JLabel("Usuario");
        user_label.setFont(normal_label);
        user_label.setBounds(0, 180, 400, 20);
        user_label.setForeground(new Color(0xffffff));
        user_label.setHorizontalAlignment(JLabel.CENTER);

        JTextField user_field = new JTextField();
        user_field.setBounds(0, 220, 400, 30);
        user_field.setText(info[1]);

        JLabel pass_label = new JLabel("Contrasena");
        pass_label.setFont(normal_label);
        pass_label.setBounds(0, 260, 400, 20);
        pass_label.setForeground(new Color(0xffffff));
        pass_label.setHorizontalAlignment(JLabel.CENTER);

        JPasswordField pass_field = new JPasswordField();
        pass_field.setBounds(0, 300, 400, 30);
        pass_field.setText(info[2]);

        JLabel bio_label = new JLabel("Biografia");
        bio_label.setFont(normal_label);
        bio_label.setBounds(0, 340, 400, 30);
        bio_label.setForeground(new Color(0xffffff));
        bio_label.setHorizontalAlignment(JLabel.CENTER);

        JTextArea bio = new JTextArea();
        bio.setBackground(new Color(0x75FA61)); //color de fuente de la biografia
        bio.setBounds(10, 390, 360, 90);
        bio.setText(info[3]);

        JButton remover = new JButton("Remover Cuenta");
        remover.setBounds(10, 500, 360, 40);

        //Para remover a un usario creamos un archivo temporal y escribimos cada linea
        // Menos la linea que coincide con la del usuario y luego borramos y renombramos
        remover.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "Esta seguro de remover su cuenta?");
                    switch (i) {
                    case 1:
                        Ventana.this.cambiarPanel("cuenta");
                        break;
                    case 0:
                        String l = info[0] + "/" + info[1] + "/" + info[2] + "/" + info[3] + "/" + info[4];
                        try {
                            FileReader fr = new FileReader("users.txt");
                            BufferedReader br = new BufferedReader(fr);
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (!(line.equals(l))) {
                                    FileWriter f = new FileWriter("temp.txt", true);
                                    f.write(line + "\n");
                                    f.flush();
                                    f.close();
                                }
                            }
                            fr.close();
                            br.close();
                            File file = new File("users.txt");
                            if(file.delete()) {
                                System.out.println("Borrado");
                                JOptionPane.showMessageDialog(null, "Usuario removido exitosamente");
                                Ventana.this.cambiarPanel("login");
                            } else {
                                System.out.println(" No Borrado");
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            File usernew = new File("temp.txt");
                            if(usernew.renameTo(new File("users.txt"))) {
                                System.out.print("Renombre exitoso");
                            } else System.out.print("fallo");
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        break;
                    }
                }
            });

        JButton actualizar = new JButton("Actualizar");
        actualizar.setBounds(10, 560, 160, 40);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(180, 560, 190, 40);

        //Lo mismo que borrar nada ma que escribimos la primera linea en el archivo temp
        //y usamos lo mismo que si fuera a ser borrado
        actualizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar su cuenta");
                    switch(i) {
                    case 0:
                        String l = info[0] + "/" + info[1] + "/" + info[2] + "/" + info[3] + "/" + info[4];
                        String newline = name_field.getText() + "/" + user_field.getText() + "/" + pass_field.getText() + "/" + bio.getText() + "/" + super_user.isSelected() + "/";
                        System.out.println(newline);
                        try {
                            FileReader fr = new FileReader("users.txt");
                            BufferedReader br = new BufferedReader(fr);
                            FileWriter fw = new FileWriter("temp.txt");
                            fw.write(newline + "\n");
                            fw.close();
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (!(line.equals(l))) {
                                    FileWriter f = new FileWriter("temp.txt", true);
                                    f.write(line + "\n");
                                    f.flush();
                                    f.close();
                                }
                            }
                            fr.close();
                            br.close();
                            File file = new File("users.txt");
                            if(file.delete()) {
                                System.out.println("Borrado");
                            } else {
                                System.out.println(" No Borrado");
                            }
                        } catch(IOException ee) {
                            ee.printStackTrace();
                        }
                        try {
                            File usernew = new File("temp.txt");
                            if(usernew.renameTo(new File("users.txt"))) {
                                JOptionPane.showMessageDialog(null, "Datos Actualizados");
                                System.out.print("Renombre exitoso");
                            } else System.out.print("fallo");
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        break;
                    case 1:
                        Ventana.this.cambiarPanel("cuenta");
                        break;
                    }
                }
            });
        cancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "Desea salir de verdad?");
                    if (i == 0) System.exit(0);
                }
            });

        cuenta.add(text);
        cuenta.add(name_label);
        cuenta.add(name_field);
        cuenta.add(user_label);
        cuenta.add(user_field);
        cuenta.add(pass_label);
        cuenta.add(pass_field);
        cuenta.add(bio_label);
        cuenta.add(bio);
        cuenta.add(remover);
        cuenta.add(actualizar);
        cuenta.add(cancelar);
        cuenta.add(super_user);
        this.add(cuenta);
    }

    public void show_bloquear() {
        panel_activo = "bloquear";
        bloquear = new JPanel();
        bloquear.setLayout(null);
        bloquear.setBounds(0, 0, 400, 700);
        JLabel text = new JLabel("Bloquear Cuenta");
        text.setFont(header);
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setBounds(0, 0, 400, 30);

        JPanel cosas = new JPanel();
        cosas.setLayout(null);
        cosas.setBounds(10, 100, 360, 400);
        cosas.setBackground(new Color(0xffffff));

        JLabel userx = new JLabel("Usuario");
        userx.setBounds(0, 20, 360, 20);
        userx.setFont(normal_label);
        userx.setHorizontalAlignment(JLabel.CENTER);

        JTextField userf = new JTextField();
        userf.setBounds(10, 60, 330, 30);

        JLabel passx = new JLabel("Contrasena");
        passx.setBounds(0, 100, 360, 20);
        passx.setFont(normal_label);
        passx.setHorizontalAlignment(JLabel.CENTER);

        JTextField passf = new JTextField();
        passf.setBounds(10, 140, 330, 30);

        cosas.add(userx);
        cosas.add(userf);
        cosas.add(passx);
        cosas.add(passf);
        JButton block = new JButton("Bloquear");

        block.setBounds(10, 520, 190, 40);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(210, 520, 160, 40);

        block.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int des = JOptionPane.showConfirmDialog(null, "Esta seguro de bloquear cuenta?");
                    String linex = "";
                    for (String a: info ) {
                        linex += a + "/";
                    }
                    if (des == 0) {
                        if (userf.getText().equals(info[1]) && passf.getText().equals(info[2])) {
                            //se escribe el usuario para identificarlo en un archivo de texto
                            try {
                                String user = userf.getText();
                                String pass = passf.getText();
                                String text = user + "/" + pass;
                                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                    System.out.println(linex);
                                    if (linex.equals(line + "/")) {
                                        JOptionPane.showMessageDialog(null, "Tu Cuenta a sido bloqueada");
                                        System.out.println("Usuario encontrado");
                                        FileWriter fr = new FileWriter(new File("usuarios_bloqueados.txt"), true);
                                        fr.append(line + "\n");
                                        fr.close();
                                        Ventana.this.cambiarPanel("splash");
                                        loggeado = false;
                                    }
                                }
                                br.close();
                            } catch (IOException ee) {
                                ee.printStackTrace();
                            }
                        }
                    } else {
                        Ventana.this.cambiarPanel("cuenta");
                    }
                }
            });

        cancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Ventana.this.cambiarPanel("cuenta");
                }
            });
        bloquear.add(cosas);
        bloquear.add(text);
        bloquear.add(block);
        bloquear.add(cancelar);
        this.add(bloquear);
    }

    public void show_listausuarios() {
        panel_activo = "listausuarios";
        listausuarios = new JPanel();
        listausuarios.setBounds(0, 0, 400, 700);
        listausuarios.setLayout(null);

        JLabel header_lu = new JLabel("Listar Usarios");
        header_lu.setFont(header);
        header_lu.setBounds(0, 0, 400, 30);
        header_lu.setHorizontalAlignment(JLabel.CENTER);
        String column[] = { "Nombre", "Usuario", "Contrasena", "Biografia", "Administrador"};
        try {
            FileReader fr = new FileReader("users.txt");
            BufferedReader users = new BufferedReader(fr);
            String line;
            String dato = "";
            while((line = users.readLine()) != null) {
                dato += line;
            }
            String [] l = dato.split("/");
            String data[][] = Ventana.this.stringToMatrix(l);
            for (int i = 0; i < data.length; i++) { //esto es igual a la fila en nuestra matriz.
                for (int j = 0; j < data[i].length; j++) { //esto es igual a la columna en cada fila
                    System.out.print(data[i][j] + " ");
                }
                System.out.println(); //cambie la línea en la consola cuando la fila llegue a su fin en la matriz.
            }
            JTable jt = new JTable(data, column);
            jt.setBounds(0, 50, 400, 700);
            JScrollPane sp = new JScrollPane(jt);
            sp.setBounds(0, 50, 400, 700);
            listausuarios.add(sp);
            Ventana.this.validate();
            Ventana.this.repaint();

        } catch (IOException ee) {
            ee.printStackTrace();
        }
        listausuarios.add(header_lu);
        this.add(listausuarios);
    }

    public void cambiarPanel(String objetivo) {
        if (panel_activo.equals("splash")) {
            Ventana.this.remove(splash);
        }
        if (panel_activo.equals("login")) {
            Ventana.this.remove(login);
        }
        if (panel_activo.equals("signin")) {
            Ventana.this.remove(signin);
        }
        if (panel_activo.equals("cuenta")) {
            Ventana.this.remove(cuenta);
        }
        if (panel_activo.equals("bloquear")) {
            Ventana.this.remove(bloquear);
        }
        if (panel_activo.equals("listausuarios")) {
            Ventana.this.remove(listausuarios);
        }

        if (objetivo.equals("splash")) {
            Ventana.this.show_splash();
        }
        if (objetivo.equals("signin")) {
            Ventana.this.show_signin();
        }
        if (objetivo.equals("login")) {
            Ventana.this.show_login();
        }
        if (objetivo.equals("cuenta")) {
            Ventana.this.show_cuenta();
        }
        if (objetivo.equals("bloquear")) {
            Ventana.this.show_bloquear();
        }
        if (objetivo.equals("listausuarios")) {
            Ventana.this.show_listausuarios();
        }
        this.validate();
        this.repaint();
    }

    public String[][] stringToMatrix(String cadena[]) {
        int r = 0, c = 0;
        String[][] records = new String[(cadena.length / 5) + 1][5];
        for (String element : cadena) {
            records[r][c] = element;
            c++;
            if (c == 5) {
                c = 0;
                r++;
            }
        }
        return records;
    }
}
