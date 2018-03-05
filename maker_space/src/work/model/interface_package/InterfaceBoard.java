/**
 * <pre>
 * @author DONGHYUNLEE HANAJUNG YOUNGHWANGBO
 * @version ver.1.0
 * @since jdk1.8
 * </pre>
 */
package work.model.interface_package;

import java.util.ArrayList;

import work.model.dto.IdeaBoard;

/**
 * Board �������̽�
 * ��� ������
 * 1) �߰�
 * 2) ����
 * 3) ����
 * 4) ��ȸ 
 * - �̸�
 * - ����
 * - �ؽ��±� 
 */
public interface InterfaceBoard {

	/**
	 * 1. �߰�
	 * @param dto
	 * @return
	 */
	public int registerBoard(IdeaBoard dto);
	/**
	 * 2. ����
	 * @param title
	 * @param content
	 * @param result
	 * @param files
	 * @return
	 */
	public int changeBoard(String boardIdx, String title, String content, String result, String files);
	/**
	 * 3. ����
	 * @param boardIdx
	 * @return
	 */
	public int removeBoard(String boardIdx);
	/**
	 * 4. ��ȸ
	 * @param name
	 * @return
	 */
	public ArrayList<IdeaBoard> findBoardName(String name);
	public ArrayList<IdeaBoard> findBoardTitle(String title);
	public ArrayList<IdeaBoard> findBoardContent(String content);
	public ArrayList<IdeaBoard> findBoardHashTag(String hashTag);
}