package mm.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import mm.model.Colore;

public class ColoreComboBox extends JComboBox<Colore>
{
	private static final long serialVersionUID = 1L;

	public ColoreComboBox()
	{
		super(Colore.values());
		setRenderer(new ColoreComboBoxRenderer());
	}
}

class ColoreComboBoxRenderer extends JPanel implements ListCellRenderer<Colore>
{
	private static final long serialVersionUID = 1L;
	
	private JPanel colorPanel;
	private JLabel colorLabel;

	public ColoreComboBoxRenderer()
	{
		setOpaque(true);
		setLayout(new GridLayout(1, 2));
		
		setBorder(new EmptyBorder(3, 3, 3, 3));
		
		colorLabel = new JLabel();
		add(colorLabel, BorderLayout.LINE_START);
		
		colorPanel = new JPanel();		
		add(colorPanel, BorderLayout.CENTER);		
	}
	
	@Override
	public Component getListCellRendererComponent(
            JList<? extends Colore> list,
            Colore value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
	{
        if (isSelected) 
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } 
        else 
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        colorLabel.setText(value.toString());
        colorPanel.setBackground(value.getInnerColor());        

        return this;
	}
	
}
