package ed.ui;

import javax.swing.JOptionPane;

public class SwingMessageManager implements MessageManager
{

	@Override
	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(null, message);
	}
	
}
