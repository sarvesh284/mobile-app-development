from flask import Flask, request, send_file
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle
from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT, TA_CENTER
import io

app = Flask(__name__)

def create_ats_resume(data):
    """
    Create an ATS-friendly resume PDF from dict data.
    Professional formatting with clean layout and proper spacing.
    """
    buffer = io.BytesIO()
    doc = SimpleDocTemplate(
        buffer,
        pagesize=letter,
        topMargin=0.2*inch,
        bottomMargin=0.2*inch,
        leftMargin=0.6*inch,
        rightMargin=0.6*inch
    )
    
    story = []
    styles = getSampleStyleSheet()
    
    primary_color = colors.HexColor('#1a1a1a')
    accent_color = colors.HexColor('#2c3e50')
    line_color = colors.HexColor('#333333')
    
    name_style = ParagraphStyle('CustomName', parent=styles['Heading1'], fontSize=20, textColor=primary_color, spaceAfter=4, alignment=TA_CENTER, fontName='Helvetica-Bold', leading=24, letterSpacing=1)
    subtitle_style = ParagraphStyle('SubtitleStyle', parent=styles['Normal'], fontSize=11, textColor=accent_color, alignment=TA_CENTER, spaceAfter=8, fontName='Helvetica-Bold', leading=14)
    contact_style = ParagraphStyle('ContactStyle', parent=styles['Normal'], fontSize=9.5, textColor=primary_color, alignment=TA_CENTER, spaceAfter=16, fontName='Helvetica', leading=12)
    section_style = ParagraphStyle('SectionHeading', parent=styles['Heading2'], fontSize=12, textColor=primary_color, spaceAfter=10, spaceBefore=16, fontName='Helvetica-Bold', borderWidth=0, borderPadding=0, leading=14, letterSpacing=0.5)
    job_title_style = ParagraphStyle('JobTitle', parent=styles['Normal'], fontSize=10.5, textColor=primary_color, fontName='Helvetica-Bold', spaceAfter=2, spaceBefore=8, leading=13)
    company_style = ParagraphStyle('CompanyStyle', parent=styles['Normal'], fontSize=10, textColor=accent_color, fontName='Helvetica', spaceAfter=6, leading=12)
    date_style = ParagraphStyle('DateStyle', parent=styles['Normal'], fontSize=10, textColor=accent_color, fontName='Helvetica', alignment=TA_LEFT, leading=12)
    normal_style = ParagraphStyle('CustomNormal', parent=styles['Normal'], fontSize=10, textColor=primary_color, spaceAfter=10, leftIndent=0, fontName='Helvetica', leading=14, alignment=TA_LEFT)
    bullet_style = ParagraphStyle('BulletStyle', parent=styles['Normal'], fontSize=10, textColor=primary_color, spaceAfter=4, leftIndent=0.15*inch, bulletIndent=0, fontName='Helvetica', leading=13)
    
    # Header
    story.append(Paragraph(data.get('full_name', '').upper(), name_style))
    if data.get('job_title'):
        story.append(Paragraph(data.get('job_title', ''), subtitle_style))
    
    contact_parts = []
    if data.get('location'): contact_parts.append(data['location'])
    if data.get('email'): contact_parts.append(data['email'])
    if data.get('phone'): contact_parts.append(data['phone'])
    if data.get('linkedin'): contact_parts.append(data['linkedin'])
    if data.get('website'): contact_parts.append(data['website'])
    
    if contact_parts:
        story.append(Paragraph(' | '.join(contact_parts), contact_style))
    
    from reportlab.platypus import HRFlowable
    story.append(HRFlowable(width="100%", thickness=1.5, color=line_color, spaceAfter=14, spaceBefore=2))
    
    # Summary
    if data.get('summary'):
        story.append(Paragraph('<b>PROFESSIONAL SUMMARY</b>', section_style))
        story.append(HRFlowable(width="100%", thickness=0.5, color=line_color, spaceAfter=8, spaceBefore=0))
        story.append(Paragraph(data['summary'], normal_style))
    
    # Experience (Simple single block or parsed)
    if data.get('experience'):
        story.append(Paragraph('<b>WORK EXPERIENCE</b>', section_style))
        story.append(HRFlowable(width="100%", thickness=0.5, color=line_color, spaceAfter=8, spaceBefore=0))
        exp_lines = data['experience'].split('\n')
        for line in exp_lines:
            line = line.strip()
            if line:
                if line.startswith('•') or line.startswith('-') or line.startswith('*'):
                    line = line[1:].strip()
                story.append(Paragraph(f"• {line}", bullet_style))
    
    # Education
    if data.get('education'):
        story.append(Paragraph('<b>EDUCATION</b>', section_style))
        story.append(HRFlowable(width="100%", thickness=0.5, color=line_color, spaceAfter=8, spaceBefore=0))
        edu_lines = data['education'].split('\n')
        for line in edu_lines:
            line = line.strip()
            if line:
                if line.startswith('•') or line.startswith('-') or line.startswith('*'):
                    line = line[1:].strip()
                story.append(Paragraph(f"• {line}", bullet_style))
                
    # Skills
    if data.get('skills'):
        story.append(Paragraph('<b>SKILLS</b>', section_style))
        story.append(HRFlowable(width="100%", thickness=0.5, color=line_color, spaceAfter=8, spaceBefore=0))
        skill_lines = data['skills'].split('\n')
        for line in skill_lines:
            line = line.strip()
            if line:
                if ':' in line and not line.startswith('•') and not line.startswith('-'):
                    story.append(Paragraph(f"<b>{line.split(':')[0]}:</b> {':'.join(line.split(':')[1:])}", normal_style))
                else:
                    if line.startswith('•') or line.startswith('-') or line.startswith('*'):
                        line = line[1:].strip()
                    story.append(Paragraph(f"• {line}", bullet_style))

    # Projects
    if data.get('projects'):
        story.append(Paragraph('<b>PROJECTS</b>', section_style))
        story.append(HRFlowable(width="100%", thickness=0.5, color=line_color, spaceAfter=8, spaceBefore=0))
        project_lines = data['projects'].split('\n')
        for line in project_lines:
            line = line.strip()
            if line:
                if line.startswith('•') or line.startswith('-') or line.startswith('*'):
                    line = line[1:].strip()
                story.append(Paragraph(f"• {line}", bullet_style))

    doc.build(story)
    buffer.seek(0)
    return buffer

@app.route('/generate', methods=['POST'])
def generate_resume():
    if request.is_json:
        data = request.get_json()
    else:
        data = request.form.to_dict()
        
    pdf_buffer = create_ats_resume(data)
    filename = f"resume_{data.get('full_name', 'download').replace(' ', '_')}.pdf"
    
    return send_file(
        pdf_buffer,
        mimetype='application/pdf',
        as_attachment=True,
        download_name=filename
    )

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5002)
